

package com.example.myreadwritefile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonNew.setOnClickListener(this)
        binding.btnSaveFile.setOnClickListener(this)
        binding.buttonOpenFile.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v?.id ){
            R.id.btn_save_file->{saveFile()}
            R.id.button_new->{newFile()}
            R.id.button_open_file->{showList()}
        }
    }

//    menampilkan  berkas data
    private fun showList() {
        val items = fileList().filter { filename->(filename != "profileInstalled")}.toTypedArray()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("pilih file yang diignkan")
        builder.setItems(items){dialog, item -> loadData(items[item].toString())}
    val alert = builder.create()
    alert.show()
    }

//    membuka berkas
    private fun loadData(titel : String) {

        val fileModel = FileHelper.readFromFile(this,titel)
    binding.editTitle.setText(fileModel.fileName)
    binding.editFile.setText(fileModel.data)
    Toast.makeText(this, "Loading"+fileModel.fileName+ "data", Toast.LENGTH_SHORT).show()
    }

    //     baru menyimpan data
    fun newFile(){
        binding.editFile.setText("")
    binding.editTitle.setText("")
    Toast.makeText(this,"clearing file",Toast.LENGTH_LONG).show()

    }

    private fun saveFile() {
        when {
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            binding.editFile.text.toString().isEmpty() -> Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            else -> {
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()
                val fileModel = FileModel()
                fileModel.fileName = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving " + fileModel.fileName + " file", Toast.LENGTH_SHORT).show()
            }
        }
    }

}