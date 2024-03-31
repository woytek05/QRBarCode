package com.example.qrbarcode.ui.home;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrbarcode.MainActivity;
import com.example.qrbarcode.databinding.FragmentHomeBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private ActivityResultLauncher<Intent> scanResultLauncher;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        scanResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String scannedResult = IntentIntegrator.parseActivityResult(IntentIntegrator.REQUEST_CODE, result.getResultCode(), data).getContents();
                        viewModel.setScannedResult(scannedResult);
                        Log.d("ELO", scannedResult);
                    }
                });

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button buttonScan = binding.button;
        buttonScan.setOnClickListener(v -> startScan());

        Button buttonCopy = binding.buttonCopy;
        buttonCopy.setOnClickListener(v -> ((MainActivity)getActivity()).copyToClipboard(viewModel.getScannedResult().getValue()));
        buttonCopy.setVisibility(View.GONE);

        Button buttonShare = binding.buttonShare;
        buttonShare.setOnClickListener(v -> share(v, viewModel.getScannedResult().getValue()));
        buttonShare.setVisibility(View.GONE);

        TextView textViewResult = binding.textView;
        viewModel.getScannedResult().observe(getViewLifecycleOwner(), result -> {
            textViewResult.setText("Result: " + result);
            buttonCopy.setVisibility(View.VISIBLE);
            buttonShare.setVisibility(View.VISIBLE);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startScan() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a QR Code or a Bar Code");
        integrator.setCameraId(0);
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        Intent intent = integrator.createScanIntent();
        scanResultLauncher.launch(intent);
    }

    public void share(View v, String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}