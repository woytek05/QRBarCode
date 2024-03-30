package com.example.qrbarcode.ui.qrcode;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.qrbarcode.databinding.FragmentQRCodeBinding;

public class QRCodeFragment extends Fragment {
    private QRCodeViewModel viewModel;
    private FragmentQRCodeBinding binding;

    public static QRCodeFragment newInstance() {
        return new QRCodeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(QRCodeViewModel.class);

        binding = FragmentQRCodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText editText = binding.editText;
        final Button buttonGenerateCode = binding.buttonGenerateCode;
        final ImageView imageView = binding.imageView;

        viewModel.getQRCodeBitmap().observe(getViewLifecycleOwner(), bitmap -> {
            if (bitmap != null) imageView.setImageBitmap(bitmap);
        });

        buttonGenerateCode.setOnClickListener(v -> viewModel.generateQRCode(editText.getText().toString(), 500, 500));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}