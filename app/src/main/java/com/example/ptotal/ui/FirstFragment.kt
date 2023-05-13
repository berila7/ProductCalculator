package com.example.ptotal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.ptotal.R
import com.example.ptotal.databinding.FragmentFirstBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    private var tax: Float = 0.0f
    private var prixu: Float = 0.0f

    private var categorieItemSelected = false
    private var produitItemSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinners()
        setupListeners()
    }

    private fun setupSpinners() {
        // spinner for the categories and products
        val categories = resources.getStringArray(R.array.catégorie)
        val produit = resources.getStringArray(R.array.produit)
        val categorieAdapter = ArrayAdapter(requireContext(), R.layout.categories_items, categories)
        val produitAdapter = ArrayAdapter(requireContext(), R.layout.categories_items, produit)
        binding.spinnerC.setAdapter(categorieAdapter)
        binding.spinnerP.setAdapter(produitAdapter)
    }

    private fun setupListeners() {
        binding.button.setOnClickListener { showFinalCalculation() }
        setupCategorySpinnerListener()
        setupProductSpinnerListener()
        binding.button2.setOnClickListener { onResetBtn() }
    }


    private fun setupCategorySpinnerListener() {
        binding.spinnerC.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            categorieItemSelected = true
            when (position) {
                1 -> tax = 0.2f
                2 -> tax = 0.3f
                3 -> tax = 0.5f
            }
        }
    }

    private fun setupProductSpinnerListener() {
        binding.spinnerP.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            produitItemSelected = true
            when (position) {
                0 -> {
                    prixu = 5000.0f
                    binding.tvPrix.text = getString(R.string.items_prix, prixu.toString())
                }
                1 -> {
                    prixu = 4000.0f
                    binding.tvPrix.text = getString(R.string.items_prix, prixu.toString())
                }
                2 -> {
                    prixu = 7500.0f
                    binding.tvPrix.text = getString(R.string.items_prix, prixu.toString())
                }
                3 -> {
                    prixu = 15000.0f
                    binding.tvPrix.text = getString(R.string.items_prix, prixu.toString())
                }
            }
        }
    }

    private fun showFinalCalculation() {
        val inputQnt = binding.etQuantite.text.toString()
        val errorMessage = when {
            !categorieItemSelected -> "The Categorie is required"
            !produitItemSelected -> "The Produit is required"
            inputQnt.isEmpty() -> "The Quantity option is required"
            binding.radioGrp.checkedRadioButtonId == -1 -> "The Delivery option is required"
            else -> null
        }

        if (errorMessage != null) {
            showSnackbar(binding.root, errorMessage)
        } else {
            showFinalDailog()
        }
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(ANIMATION_MODE_SLIDE)
            .show()
    }

    fun onResetBtn() {
        binding.spinnerC.text = null
        binding.spinnerC.isFocusable = false
        binding.spinnerP.text = null
        binding.spinnerP.isFocusable = false
        binding.tvPrix.text = "0.00 Dh"
        binding.lRapide.isChecked = false
        binding.emballage.isChecked = false
        binding.radioGrp.clearCheck()
        binding.etQuantite.text = null
        binding.etQuantite.isFocusable = false
        categorieItemSelected = false
        produitItemSelected = false
    }
    private fun showFinalDailog() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_dialog, null)
        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setNegativeButton("Cancel") { _, _ ->
            }
            .setPositiveButton("Ok") { _, _ ->

            }
            .show()
    }
}