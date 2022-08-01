package com.frag.weather.View

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.Uri.fromParts
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.frag.weather.R
import com.frag.weather.databinding.FragmentPermissionBinding
import kotlinx.coroutines.launch
import java.net.URI

class PermissionFragment : Fragment(R.layout.fragment_permission) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", this.context?.applicationContext?.packageName, null)
        intent.setData(uri)
        view.findViewById<Button>(R.id.permission_fragment_button).setOnClickListener {
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
