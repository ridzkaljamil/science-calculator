package com.example.myapplication;

import net.objecthunter.exp4j.operator.Operator;

import java.util.ArrayList;
import java.util.List;

public class Operators {
    public static List<Operator> getOperators() {
        List<Operator> operators = new ArrayList<>();

        // Operator factorial (!)
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if (arg != args[0]) {
                    throw new IllegalArgumentException("Factorial hanya berlaku untuk angka bulat");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("Factorial dari angka negatif tidak didefinisikan");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        // Operator permutasi (P)
        Operator permutation = new Operator("P", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
            @Override
            public double apply(double... args) {
                final int n = (int) args[0];
                final int r = (int) args[1];

                if (n < 0 || r < 0) {
                    throw new IllegalArgumentException("Argumen negatif tidak diizinkan");
                }
                if (r > n) {
                    throw new IllegalArgumentException("r tidak boleh lebih besar dari n");
                }

                // Hitung nPr = n! / (n-r)!
                double result = 1;
                for (int i = n; i > n - r; i--) {
                    result *= i;
                }
                return result;
            }
        };

        // Operator kombinasi (C)
        Operator combination = new Operator("C", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
            @Override
            public double apply(double... args) {
                final int n = (int) args[0];
                final int r = (int) args[1];

                if (n < 0 || r < 0) {
                    throw new IllegalArgumentException("Argumen negatif tidak diizinkan");
                }
                if (r > n) {
                    throw new IllegalArgumentException("r tidak boleh lebih besar dari n");
                }

                // Hitung nCr = n! / (r! * (n-r)!)
                return calculateFactorial(n) / (calculateFactorial(r) * calculateFactorial(n - r));
            }

            private double calculateFactorial(int number) {
                double result = 1;
                for (int i = 1; i <= number; i++) {
                    result *= i;
                }
                return result;
            }
        };

        // Tambahkan operator ke dalam list
        operators.add(factorial);
        operators.add(permutation);
        operators.add(combination);

        return operators;
    }
}