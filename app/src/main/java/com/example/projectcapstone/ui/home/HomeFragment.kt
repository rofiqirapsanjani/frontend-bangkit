package com.example.projectcapstone.ui.home

import android.Manifest
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.R
import com.example.projectcapstone.adapter.ProductsAdapter
import com.example.projectcapstone.authentication.register.RegisterActivity
import com.example.projectcapstone.databinding.FragmentHomeBinding
import com.example.projectcapstone.databinding.FragmentProfileBinding
import com.example.projectcapstone.helper.Helper.rotateBitmap
import com.example.projectcapstone.network.response.Product
import com.example.projectcapstone.network.response.ProductResponse
import com.example.projectcapstone.ui.ProductDetail
import com.example.projectcapstone.ui.camera.CameraActivity
import com.example.projectcapstone.ui.camera.CaptureImage
import com.example.projectcapstone.ui.mystore.MyStoreActivity
import java.io.File

class HomeFragment : Fragment() {
    private lateinit var rvproducts: RecyclerView
    private lateinit var products: List<Product>
    private var resultCategory: String = "null"
    private var getFile: File? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        rvproducts = root.findViewById(R.id.rv_products)

//      buat ambil hasil kategori gambar dari prediksi model
        resultCategory = requireActivity().intent.getStringExtra("category").toString()
        System.out.println("category " + resultCategory)

        binding.apply {
            pressSearch.setOnClickListener {
                startActivity(Intent(activity, CaptureImage::class.java))
            }
            btnCamera.setOnClickListener {
                startActivity(Intent(activity, CaptureImage::class.java))
            }
            if (resultCategory != "null") {
                pressSearch.visibility = View.INVISIBLE
                cancelSearch.visibility = View.VISIBLE
            } else {
                pressSearch.visibility = View.VISIBLE
                cancelSearch.visibility = View.INVISIBLE
            }

            cancelSearch.setOnClickListener {
                pressSearch.visibility = View.VISIBLE
                cancelSearch.visibility = View.INVISIBLE
                getAllProducts()
                homeViewModel.products.observe(viewLifecycleOwner) {
                    products = it!!
                    showProducts(products)
                    resultCategory = "null"
                }
            }
        }
        return root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        if (resultCategory == "null") {
            getAllProducts()
            homeViewModel.products.observe(viewLifecycleOwner){
                products = it!!
                showProducts(products)
            }
        }else {
            System.out.println(resultCategory + " tampilannya")
            getProductbyCategory(resultCategory)
            resultCategory = "null"
            System.out.println(resultCategory + " tampilannya")
            homeViewModel.products.observe(viewLifecycleOwner){
                products = it!!
                showProducts(products)
            }
        }
//        binding.frameLayout.setOnClickListener {
//            startCamera()
//
//        }

    }

    fun getAllProducts() {
        homeViewModel.fetchProducts()
    }
    fun getProductbyCategory(category : String) {
        homeViewModel.getProductbyCategory(category)
    }
    fun showProducts(products: List<Product>){
        rvproducts.layoutManager = LinearLayoutManager(activity)
        val adapter = ProductsAdapter(products)
        rvproducts.adapter = adapter
        adapter.setOnItemClickCallback(object : ProductsAdapter.OnItemClickCallback{
            override fun onItemClicked(product: Product) {
                val intent = Intent(activity, ProductDetail::class.java)
                intent.putExtra("product", product)
                System.out.println("getclicked")
                startActivity(intent)
            }
        })

    }
//    private fun cameraButton() {
//        binding.btnCamera.setOnClickListener {
//            startCamera()
//        }
//    }

    private fun startCamera() {
        val intent = Intent(this@HomeFragment.requireContext(), CameraActivity::class.java)
        launcherCamera.launch(intent)
    }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(BitmapFactory.decodeFile(getFile?.path), isBackCamera)

        }
    }



    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}