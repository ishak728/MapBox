package com.ishak.myapplication.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.ishak.myapplication.databinding.FragmentWhereToBinding


class WhereToFragment : Fragment() {

    lateinit var permissionResultLauncher:ActivityResultLauncher<String>
    lateinit var binding:FragmentWhereToBinding

    lateinit var locationManager:LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentWhereToBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.TextInputEditText.setOnClickListener{

            if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)){
                    //show snacbar or alertdioloog
                    showRationaleDialog()
                }
                else{
                    permissionResultLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            else{
                val action=WhereToFragmentDirections.actionWhereToFragment2ToMapFragment()
                findNavController().navigate(action)
            }
        }

    }

    private fun showRationaleDialog() {

        AlertDialog.Builder(requireContext())
            .setTitle("Location Permission Required")
            .setMessage("We need your location permission for the app to function properly.")
            .setPositiveButton("Give Permission") { dialog, _ ->
                //close dialog
                dialog.dismiss()
                permissionResultLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(requireContext(),"Permission Denied",Toast.LENGTH_SHORT).show()
            }
            .show()
    }


    @SuppressLint("ServiceCast")
    fun registerLauncher(){
        permissionResultLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){result->
            if (result){
                locationManager= requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    val latlong=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (latlong!=null){
                        println("latlong"+latlong.latitude+latlong.longitude)
                    }
                    else{
                        println("latlong is nulll")
                    }
                    println("lsdfgsdfgsdfg")
                }

                val action=WhereToFragmentDirections.actionWhereToFragment2ToMapFragment()
                findNavController().navigate(action)
            }else{
                Toast.makeText(requireContext(),"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }



}