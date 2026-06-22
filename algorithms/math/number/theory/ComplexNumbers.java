package algorithms.math.number.theory;

public class ComplexNumbers {

    private static class ComplexNumber {
        double real;
        double imaginary;

        public ComplexNumber(double real, double imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }
    }

    private static ComplexNumber add(ComplexNumber number1, ComplexNumber number2) {
        return new ComplexNumber(number1.real + number2.real, number1.imaginary + number2.imaginary);
    }

    private static ComplexNumber subtract(ComplexNumber number1, ComplexNumber number2) {
        return new ComplexNumber(number1.real - number2.real, number1.imaginary - number2.imaginary);
    }

    private static ComplexNumber multiply(ComplexNumber number1, ComplexNumber number2) {
        double real = number1.real * number2.real - number1.imaginary * number2.imaginary;
        double imaginary = number1.real * number2.imaginary + number1.imaginary * number2.real;
        return new ComplexNumber(real, imaginary);
    }
}
