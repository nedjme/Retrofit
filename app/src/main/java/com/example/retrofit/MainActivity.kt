package com.example.retrofit


import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronawatch.Retrofit.IAPI
import com.example.coronawatch.Retrofit.RetrofitClient
import com.example.taskmanager.ToDos
import com.example.taskmanager.ToDosItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    lateinit var jsonAPI: IAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClient.instance
        jsonAPI = retrofit.create(IAPI::class.java)


        rv_home.layoutManager = LinearLayoutManager(this)
        fetchToDos()


        fab_dashboard.setOnClickListener {

            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
            val todo_title = view.findViewById<EditText>(R.id.ev_task)
            dialog.setView(view)

            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (todo_title.text.isNotEmpty()) {
                    val todo = ToDosItem ()
                    todo.title = todo_title.text.toString()
                    addToDo(todo)
                    fetchToDos()

                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
            }
            dialog.show()
        }


    }

   fun fetchToDos() {


       val retrofit = RetrofitClient.instance
       val jsonAPI = retrofit.create(IAPI::class.java)

        compositeDisposable.add( jsonAPI.todos.subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .subscribe( {todos -> displayToDos ( todos ) } ,
                { throwable -> Toast.makeText(this@MainActivity, throwable.message , Toast.LENGTH_LONG).show()
                } )
        )
    }

  fun displayToDos (todos : ToDos ) {
        rv_home.adapter = ToDosAdapter( this , todos , R.layout.rv_child_dashboard)
    }

    fun addToDo(todo: ToDosItem) {

        jsonAPI.addToDo(todo.title,todo.userId , todo.completed ).enqueue( object: Callback<ToDosItem> {

            override fun onResponse(call: Call<ToDosItem>, response: retrofit2.Response<ToDosItem>?) {
                if ((response != null) && (response.code() == 200)) {
                    Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_LONG).show()
                    Log.e("done add" , response.body().toString())

                }
            }
            override fun onFailure(call: Call<ToDosItem>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Echec", Toast.LENGTH_LONG).show()
            }
        })

        /*compositeDisposable.add( jsonAPI.addToDo(todo.title,todo.userId,todo.completed)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe{ user -> Log.e("add todo " , user.toString()) }
        )*/
    }


    fun updateToDo(todo: ToDosItem) {

        jsonAPI.updateToDO(todo.id , todo ).enqueue( object: Callback<ToDosItem> {

            override fun onResponse(call: Call<ToDosItem>, response: retrofit2.Response<ToDosItem>?) {
                if ((response != null) && (response.code() == 200)) {
                    Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_LONG).show()
                    Log.e("done update" , response.body().toString())

                }
            }
            override fun onFailure(call: Call<ToDosItem>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Echec", Toast.LENGTH_LONG).show()
            }
        })

        /*compositeDisposable.add( jsonAPI.updateToDO(todo.id , todo)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe{ user -> Log.e("update todo " , user.toString())}
        )*/

    }

    fun deleteToDo ( todo: ToDosItem) {

        jsonAPI.deleteToDo(todo.id).enqueue( object: Callback<Void> {

            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>?) {
                if ((response != null) && (response.code() == 200)) {
                    Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_LONG).show()
                    Log.e("done delete" , response.body().toString())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Echec", Toast.LENGTH_LONG).show()
            }
        })



}



}


