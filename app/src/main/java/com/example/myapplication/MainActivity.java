package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import android.util.Log;
import android.widget.Toast;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Deklarasi komponen UI
    private TextView tvInput, tvResult;
    private Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    private Button bSin, bCos, bTan, bLog, bLn, bPhi;
    private Button bNpr, bNcr, bFact, bAb, bAkar, bRound;
    private Button bAc, bC, bKurung, bDelete;
    private Button bPlus, bMin, bMul, bDiv, bEqual, bDot;
    private Switch lightMode;

    // Variabel untuk menyimpan ekspresi dan hasil
    private StringBuilder currentExpression = new StringBuilder();
    private double result = 0;
    private boolean isInParentheses = false;
    private int openParentheses = 0;
    private boolean lastIsOperator = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi komponen UI
        initializeViews();

        // Set dark mode secara otomatis
        setDarkModeOnStart();

        // Pengecekan debugging untuk TextView
        if (tvInput == null || tvResult == null) {
            Log.e("Calculator", "TextViews not initialized correctly!");
            Toast.makeText(this, "UI initialization error", Toast.LENGTH_LONG).show();
        } else {
            Log.d("Calculator", "TextViews initialized successfully");
            tvInput.setText("");
            tvResult.setText("0");
        }

        // Set listener untuk semua tombol
        setClickListeners();
    }

    private void setDarkModeOnStart() {
        // Set switch ke posisi dark mode
        lightMode.setChecked(false);

        // Panggil method toggle theme untuk set ke dark mode
        toggleTheme();
    }

    private void initializeViews() {
        // TextView untuk input dan hasil
        tvInput = findViewById(R.id.tvInput);
        tvResult = findViewById(R.id.tvResult);

        // Tombol angka
        b0 = findViewById(R.id.b0);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);

        // Tombol fungsi matematika dasar
        bPlus = findViewById(R.id.bplus);
        bMin = findViewById(R.id.bmin);
        bMul = findViewById(R.id.bmul);
        bDiv = findViewById(R.id.bdiv);
        bEqual = findViewById(R.id.bequal);
        bDot = findViewById(R.id.bdot);

        // Tombol fungsi matematika lanjut
        bSin = findViewById(R.id.bsin);
        bCos = findViewById(R.id.bcos);
        bTan = findViewById(R.id.btan);
        bLog = findViewById(R.id.blog);
        bLn = findViewById(R.id.bln);
        bPhi = findViewById(R.id.bphi);
        bNpr = findViewById(R.id.bnpr);
        bNcr = findViewById(R.id.ncr);
        bFact = findViewById(R.id.bfact);
        bAb = findViewById(R.id.bab);
        bAkar = findViewById(R.id.bakar);
        bRound = findViewById(R.id.bround);

        // Tombol kontrol
        bAc = findViewById(R.id.bac);
        bC = findViewById(R.id.bc);
        bKurung = findViewById(R.id.bkurung);
        bDelete = findViewById(R.id.bdelete);

        // Switch mode terang/gelap
        lightMode = findViewById(R.id.lightmode);
    }

    private void setClickListeners() {
        // Set listener untuk tombol angka
        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);

        // Set listener untuk operator matematika dasar
        bPlus.setOnClickListener(this);
        bMin.setOnClickListener(this);
        bMul.setOnClickListener(this);
        bDiv.setOnClickListener(this);
        bEqual.setOnClickListener(this);
        bDot.setOnClickListener(this);

        // Set listener untuk fungsi matematika lanjut
        bSin.setOnClickListener(this);
        bCos.setOnClickListener(this);
        bTan.setOnClickListener(this);
        bLog.setOnClickListener(this);
        bLn.setOnClickListener(this);
        bPhi.setOnClickListener(this);
        bNpr.setOnClickListener(this);
        bNcr.setOnClickListener(this);
        bFact.setOnClickListener(this);
        bAb.setOnClickListener(this);
        bAkar.setOnClickListener(this);
        bRound.setOnClickListener(this);

        // Set listener untuk tombol kontrol
        bAc.setOnClickListener(this);
        bC.setOnClickListener(this);
        bKurung.setOnClickListener(this);
        bDelete.setOnClickListener(this);

        // Set listener untuk switch mode terang/gelap
        lightMode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Tombol angka
        if (id == R.id.b0) {
            appendToExpression("0");
        } else if (id == R.id.b1) {
            appendToExpression("1");
        } else if (id == R.id.b2) {
            appendToExpression("2");
        } else if (id == R.id.b3) {
            appendToExpression("3");
        } else if (id == R.id.b4) {
            appendToExpression("4");
        } else if (id == R.id.b5) {
            appendToExpression("5");
        } else if (id == R.id.b6) {
            appendToExpression("6");
        } else if (id == R.id.b7) {
            appendToExpression("7");
        } else if (id == R.id.b8) {
            appendToExpression("8");
        } else if (id == R.id.b9) {
            appendToExpression("9");
        }
        // Operator matematika dasar
        else if (id == R.id.bplus) {
            appendOperator("+");
        } else if (id == R.id.bmin) {
            appendOperator("-");
        } else if (id == R.id.bmul) {
            appendOperator("×");
        } else if (id == R.id.bdiv) {
            appendOperator("÷");
        } else if (id == R.id.bdot) {
            appendToExpression(".");
        } else if (id == R.id.bequal) {
            calculateResult();
        }
        // Fungsi matematika lanjut
        else if (id == R.id.bsin) {
            appendFunction("sin(");
        } else if (id == R.id.bcos) {
            appendFunction("cos(");
        } else if (id == R.id.btan) {
            appendFunction("tan(");
        } else if (id == R.id.blog) {
            appendFunction("log(");
        } else if (id == R.id.bln) {
            appendFunction("ln(");
        } else if (id == R.id.bphi) {
            appendToExpression("π");
        } else if (id == R.id.bnpr) {
            appendOperator("P");
        } else if (id == R.id.ncr) {
            appendOperator("C");
        } else if (id == R.id.bfact) {
            appendOperator("!");
        } else if (id == R.id.bab) {
            appendOperator("^");
        } else if (id == R.id.bakar) {
            appendFunction("√(");
        } else if (id == R.id.bround) {
            appendFunction("round(");
        }
        // Tombol kontrol
        else if (id == R.id.bac) {
            clearAll();
        } else if (id == R.id.bc) {
            clearEntry();
        } else if (id == R.id.bkurung) {
            handleParentheses();
        } else if (id == R.id.bdelete) {
            deleteLastCharacter();
        }
        // Switch mode terang/gelap
        else if (id == R.id.lightmode) {
            toggleTheme();
        }
    }

    private void appendToExpression(String value) {
        currentExpression.append(value);
        updateDisplay();
        lastIsOperator = false;
    }

    private void appendOperator(String operator) {
        if (currentExpression.length() > 0 && !lastIsOperator) {
            currentExpression.append(operator);
            updateDisplay();
            lastIsOperator = true;
        }
    }

    private void appendFunction(String function) {
        currentExpression.append(function);
        updateDisplay();
        openParentheses++;
        lastIsOperator = false;
    }

    private void handleParentheses() {
        if (isInParentheses) {
            currentExpression.append(")");
            isInParentheses = false;
            openParentheses--;
        } else {
            currentExpression.append("(");
            isInParentheses = true;
            openParentheses++;
        }
        updateDisplay();
    }

    private void clearAll() {
        currentExpression = new StringBuilder();
        result = 0;
        updateDisplay();
        tvResult.setText("0");
        lastIsOperator = false;
        openParentheses = 0;
    }

    private void clearEntry() {
        if (currentExpression.length() > 0) {
            currentExpression.setLength(0);
            updateDisplay();
            lastIsOperator = false;
        }
    }

    private void deleteLastCharacter() {
        if (currentExpression.length() > 0) {
            char lastChar = currentExpression.charAt(currentExpression.length() - 1);
            currentExpression.deleteCharAt(currentExpression.length() - 1);

            // Jika menghapus kurung buka, kurangi penghitung
            if (lastChar == '(') {
                openParentheses--;
            }
            // Jika menghapus kurung tutup, tambahkan penghitung
            else if (lastChar == ')') {
                openParentheses++;
            }

            updateDisplay();
            lastIsOperator = isOperator(currentExpression.length() > 0 ?
                    currentExpression.charAt(currentExpression.length() - 1) : ' ');
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '^' || c == 'P' || c == 'C';
    }

    private void calculateResult() {
        if (currentExpression.length() > 0) {
            try {
                // Tutup kurung yang belum ditutup
                for (int i = 0; i < openParentheses; i++) {
                    currentExpression.append(")");
                }

                String expression = currentExpression.toString();

                // Log untuk debugging
                Log.d("Calculator", "Calculating: " + expression);

                // Kalkulasi hasil
                result = evaluateExpression(expression);

                // Tampilkan hasil
                tvResult.setText(formatResult(result));

                // Jangan reset input setelah kalkulasi agar pengguna bisa melihat ekspresinya
                // currentExpression = new StringBuilder(formatResult(result));

                lastIsOperator = false;
                openParentheses = 0;
            } catch (Exception e) {
                Log.e("Calculator", "Error calculating: " + e.getMessage());
                tvResult.setText("Error");
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private double evaluateExpression(String expression) {
        try {
            // Log untuk debugging
            Log.d("Calculator", "Evaluating: " + expression);

            // Ganti simbol dengan ekspresi yang didukung oleh exp4j
            expression = expression.replace("×", "*")
                    .replace("÷", "/")
                    .replace("π", String.valueOf(Math.PI));

            // Evaluasi dengan exp4j
            Expression e = new ExpressionBuilder(expression)
                    .build();

            return e.evaluate();
        } catch (Exception e) {
            Log.e("Calculator", "Error evaluating expression: " + e.getMessage());
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    private String formatResult(double result) {
        // Menghilangkan desimal tidak perlu (misal 5.0 menjadi 5)
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%s", result);
        }
    }

    private void updateDisplay() {
        tvInput.setText(currentExpression.toString());
    }

    private void toggleTheme() {
        try {
            boolean isLightMode = lightMode.isChecked();

            // Warna untuk tema
            int textColor = isLightMode ? Color.parseColor("#121212") : Color.parseColor("#FFFFFF");
            int orangeColor = Color.parseColor("#FF9A03");

            // 1. Set background dengan gambar - menggunakan DrawableLayers
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main);

            // Background image berdasarkan tema
            Drawable backgroundDrawable = getResources().getDrawable(
                    isLightMode ? R.drawable.background_light : R.drawable.background_dark);

            // Pattern matematika
            Drawable patternDrawable = getResources().getDrawable(R.drawable.pattern_math);

            // Set opacity untuk pattern berbeda berdasarkan tema
            if (isLightMode) {
                patternDrawable.setAlpha(50); // Lebih transparan di mode terang (value 0-255)
            } else {
                patternDrawable.setAlpha(255); // Lebih terlihat di mode gelap (value 0-255)
            }

            // Buat layer-list programatis
            LayerDrawable layerDrawable = new LayerDrawable(
                    new Drawable[]{backgroundDrawable, patternDrawable});

            // Set background untuk mainLayout
            mainLayout.setBackground(layerDrawable);

            // 2. Set background dan warna teks untuk TextView
            tvInput.setBackground(null); // Hapus background agar transparan
            tvResult.setBackground(null); // Hapus background agar transparan
            tvInput.setTextColor(textColor);
            tvResult.setTextColor(textColor);

            // 3. Akses container tombol dan ubah menjadi transparan - IMPLEMENTASI DISINI
            try {
                LinearLayout buttonContainer = (LinearLayout) mainLayout.getChildAt(2);
                if (buttonContainer != null) {
                    // Set background transparan
                    buttonContainer.setBackgroundColor(Color.TRANSPARENT);

                    // Juga perlu mengakses LinearLayout anak untuk memastikan semuanya transparan
                    if (buttonContainer.getChildCount() > 0) {
                        View innerContainer = buttonContainer.getChildAt(0);
                        if (innerContainer instanceof LinearLayout) {
                            ((LinearLayout) innerContainer).setBackgroundColor(Color.TRANSPARENT);

                            // Jika masih ada container bersarang lagi, buat rekursif
                            LinearLayout nestedLayout = (LinearLayout) innerContainer;
                            for (int i = 0; i < nestedLayout.getChildCount(); i++) {
                                View child = nestedLayout.getChildAt(i);
                                if (child instanceof ViewGroup) {
                                    child.setBackgroundColor(Color.TRANSPARENT);

                                    // Jika masih ada container bersarang lebih dalam
                                    if (child instanceof LinearLayout) {
                                        LinearLayout deepLayout = (LinearLayout) child;
                                        for (int j = 0; j < deepLayout.getChildCount(); j++) {
                                            View deepChild = deepLayout.getChildAt(j);
                                            if (deepChild instanceof ViewGroup) {
                                                deepChild.setBackgroundColor(Color.TRANSPARENT);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("Calculator", "Error setting transparent background: " + e.getMessage());
            }

            // 4. Buat drawable untuk tombol angka secara programatik
            GradientDrawable numButtonShape = new GradientDrawable();
            numButtonShape.setShape(GradientDrawable.RECTANGLE);
            numButtonShape.setCornerRadius(50); // Sesuaikan dengan radius di XML

            // Set warna berdasarkan tema
            if (isLightMode) {
                numButtonShape.setColor(Color.parseColor("#EDEDED")); // Warna terang
                numButtonShape.setStroke(2, Color.parseColor("#EDEDED")); // Border
            } else {
                numButtonShape.setColor(Color.parseColor("#333333")); // Warna gelap
                numButtonShape.setStroke(2, Color.parseColor("#333333")); // Border gelap
            }

            // 5. Set background tombol angka secara langsung
            b0.setBackground(numButtonShape.getConstantState().newDrawable());
            b1.setBackground(numButtonShape.getConstantState().newDrawable());
            b2.setBackground(numButtonShape.getConstantState().newDrawable());
            b3.setBackground(numButtonShape.getConstantState().newDrawable());
            b4.setBackground(numButtonShape.getConstantState().newDrawable());
            b5.setBackground(numButtonShape.getConstantState().newDrawable());
            b6.setBackground(numButtonShape.getConstantState().newDrawable());
            b7.setBackground(numButtonShape.getConstantState().newDrawable());
            b8.setBackground(numButtonShape.getConstantState().newDrawable());
            b9.setBackground(numButtonShape.getConstantState().newDrawable());

            // 6. Set background tombol fungsi dan operasi
            bDot.setBackground(numButtonShape.getConstantState().newDrawable());
            bEqual.setBackground(numButtonShape.getConstantState().newDrawable());
            bPlus.setBackground(numButtonShape.getConstantState().newDrawable());
            bMin.setBackground(numButtonShape.getConstantState().newDrawable());
            bMul.setBackground(numButtonShape.getConstantState().newDrawable());
            bDiv.setBackground(numButtonShape.getConstantState().newDrawable());
            bKurung.setBackground(numButtonShape.getConstantState().newDrawable());

            // 7. Set background tombol fungsi matematika lanjut
            bSin.setBackground(numButtonShape.getConstantState().newDrawable());
            bCos.setBackground(numButtonShape.getConstantState().newDrawable());
            bTan.setBackground(numButtonShape.getConstantState().newDrawable());
            bLog.setBackground(numButtonShape.getConstantState().newDrawable());
            bLn.setBackground(numButtonShape.getConstantState().newDrawable());
            bPhi.setBackground(numButtonShape.getConstantState().newDrawable());
            bNpr.setBackground(numButtonShape.getConstantState().newDrawable());
            bNcr.setBackground(numButtonShape.getConstantState().newDrawable());
            bFact.setBackground(numButtonShape.getConstantState().newDrawable());
            bAb.setBackground(numButtonShape.getConstantState().newDrawable());
            bAkar.setBackground(numButtonShape.getConstantState().newDrawable());
            bRound.setBackground(numButtonShape.getConstantState().newDrawable());

            // 8. Set background tombol kontrol
            bAc.setBackground(numButtonShape.getConstantState().newDrawable());
            bC.setBackground(numButtonShape.getConstantState().newDrawable());
            bDelete.setBackground(numButtonShape.getConstantState().newDrawable());

            // 9. Set warna teks tombol angka
            b0.setTextColor(textColor);
            b1.setTextColor(textColor);
            b2.setTextColor(textColor);
            b3.setTextColor(textColor);
            b4.setTextColor(textColor);
            b5.setTextColor(textColor);
            b6.setTextColor(textColor);
            b7.setTextColor(textColor);
            b8.setTextColor(textColor);
            b9.setTextColor(textColor);

            // 10. Set warna teks tombol fungsi dan operasi ke oranye
            bSin.setTextColor(orangeColor);
            bCos.setTextColor(orangeColor);
            bTan.setTextColor(orangeColor);
            bLog.setTextColor(orangeColor);
            bLn.setTextColor(orangeColor);
            bPhi.setTextColor(orangeColor);
            bNpr.setTextColor(orangeColor);
            bNcr.setTextColor(orangeColor);
            bFact.setTextColor(orangeColor);
            bAb.setTextColor(orangeColor);
            bAkar.setTextColor(orangeColor);
            bRound.setTextColor(orangeColor);
            bAc.setTextColor(orangeColor);
            bC.setTextColor(orangeColor);
            bDelete.setTextColor(orangeColor);
            bDot.setTextColor(orangeColor);
            bEqual.setTextColor(orangeColor);
            bPlus.setTextColor(orangeColor);
            bMin.setTextColor(orangeColor);
            bMul.setTextColor(orangeColor);
            bDiv.setTextColor(orangeColor);
            bKurung.setTextColor(orangeColor);

        } catch (Exception e) {
            Log.e("Calculator", "Toggle theme error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    }