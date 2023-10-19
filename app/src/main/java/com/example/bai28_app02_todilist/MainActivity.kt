package com.example.bai28_app02_todilist

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bai28_app02_todilist.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    var itemList  = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemList = fileHelper.readData(this)
        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)
        binding.lvCongViec.adapter = arrayAdapter

        binding.btnAdd.setOnClickListener {
            var itemName = binding.editInput.text.toString()
            if (itemName.isNotEmpty())
            {
                itemList.add(itemName)
                binding.editInput.setText("")
                fileHelper.writeData(itemList,applicationContext)
                arrayAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(this, "The task cannot empty", Toast.LENGTH_SHORT).show()
            }
        }

        //Xoa item cong viec sau khi hoan thanh
        binding.lvCongViec.setOnItemClickListener { parent, view, position, id ->
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Bạn đã hoàn thành công việc này, nhấn yes để xoá")
            alert.setCancelable(true)
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                //Ghi danh sach moi vao tep tren may
                fileHelper.writeData(itemList,applicationContext)
            })
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            alert.create()
            alert.show()
        }
    }
}