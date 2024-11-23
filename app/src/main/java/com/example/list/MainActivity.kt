package com.example.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.list.R.menu

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var userListView: ListView
    private val userList = mutableListOf<User>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация элементов интерфейса
        nameEditText = findViewById(R.id.nameEditText)
        ageEditText = findViewById(R.id.ageEditText)
        saveButton = findViewById(R.id.saveButton)
        userListView = findViewById(R.id.userListView)

        // Адаптер для отображения списка пользователей
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        userListView.adapter = adapter

        // Обработчик нажатия на кнопку "Сохранить"
        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val age = ageEditText.text.toString().trim()

            if (name.isNotEmpty() && age.isNotEmpty()) {
                val user = User(name, age.toInt())
                userList.add(user)
                adapter.add("${user.name}, ${user.age} лет")
                nameEditText.text.clear()
                ageEditText.text.clear()
            } else {
                Toast.makeText(this, "Введите имя и возраст", Toast.LENGTH_SHORT).show()
            }
        }

        // Удаление пользователя при нажатии на элемент списка
        userListView.setOnItemClickListener { _, _, position, _ ->
            showDeleteDialog(position)
        }
    }

    // Создание меню с пунктом "Exit"
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Обработка выбора пункта меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exitMenuItem -> {
                finish() // Закрытие приложения
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Отображение диалогового окна для подтверждения удаления
    private fun showDeleteDialog(position: Int) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Удалить пользователя")
            .setMessage("Вы уверены, что хотите удалить этого пользователя?")
            .setPositiveButton("Да") { _, _ ->
                userList.removeAt(position)
                adapter.remove(adapter.getItem(position))
            }
            .setNegativeButton("Нет", null)
            .create()
        dialog.show()
    }
}