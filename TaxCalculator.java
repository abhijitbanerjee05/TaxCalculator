import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TaxCalculator {

    private String name;
    private LocalDate dob;
    private char gender;
    private float income;
    private float investment;
    private float houseLoan;

    private static float calculatePercentOf(float amount, float percentOf){
        return (amount * percentOf) / 100;
    }

    public static void main(String[] args){
        char input = 'Y';
        Scanner sc = new Scanner(System.in);
        while (input == 'y' || input == 'Y'){
            TaxCalculator taxCalculator = new TaxCalculator();
            getInputDetails(taxCalculator, sc);

            float taxableAmount = getTaxableAmount(taxCalculator);

            if (Period.between(taxCalculator.dob, LocalDate.now()).getYears() >= 60){
                seniorCitizenTaxCalculator(taxableAmount);
            }
            else if (taxCalculator.gender == 'M' || taxCalculator.gender == 'm'){
                maleTaxCalculator(taxableAmount);
            }
            else if (taxCalculator.gender == 'F' || taxCalculator.gender == 'f'){
                femaleTaxCalculator(taxableAmount);
            }
            else {
                System.out.println("Invalid Gender Details!");
            }
            System.out.println("\nDo you want to continue [Y,N]:\t");
            input = sc.nextLine().charAt(0);
        }
        sc.close();

    }

    private static void maleTaxCalculator(float taxableAmount) {
        System.out.println("Entering male Tax Calculator");
        if (taxableAmount <= 160000){
            generateOutput(taxableAmount, 0);
        }
        else if (taxableAmount <= 300000) {
            float payableTax = calculatePercentOf(taxableAmount - 160000, 10);
            generateOutput(taxableAmount, payableTax);
        }
        else if (taxableAmount <= 500000) {
            float payableTax = calculatePercentOf(140000, 10)
                    + calculatePercentOf(taxableAmount - 300000, 20);
            generateOutput(taxableAmount, payableTax);
        }
        else {
            float payableTax = calculatePercentOf(140000, 10)
                    + calculatePercentOf(200000, 20)
                    + calculatePercentOf(taxableAmount - 500000, 30);
            generateOutput(taxableAmount, payableTax);
        }
    }

    private static void femaleTaxCalculator(float taxableAmount) {
        System.out.println("Entering female Tax Calculator");
        if (taxableAmount <= 190000){
            generateOutput(taxableAmount, 0);
        }
        else if (taxableAmount <= 300000) {
            float payableTax = calculatePercentOf(taxableAmount - 190000, 10);
            generateOutput(taxableAmount, payableTax);
        }
        else if (taxableAmount <= 500000) {
            float payableTax = calculatePercentOf(110000, 10)
                    + calculatePercentOf(taxableAmount - 300000, 20);
            generateOutput(taxableAmount, payableTax);
        }
        else {
            float payableTax = calculatePercentOf(110000, 10)
                    + calculatePercentOf(200000, 20)
                    + calculatePercentOf(taxableAmount - 500000, 30);
            generateOutput(taxableAmount, payableTax);
        }
    }

    private static void seniorCitizenTaxCalculator(float taxableAmount) {
        System.out.println("Entering senior citizen Tax Calculator");
        if (taxableAmount <= 240000){
            generateOutput(taxableAmount, 0);
        }
        else if (taxableAmount <= 300000) {
            float payableTax = calculatePercentOf(taxableAmount - 240000, 10);
            generateOutput(taxableAmount, payableTax);
        }
        else if (taxableAmount <= 500000) {
            float payableTax = calculatePercentOf(60000, 10)
                    + calculatePercentOf(taxableAmount - 300000, 20);
            generateOutput(taxableAmount, payableTax);
        }
        else {
            float payableTax = calculatePercentOf(60000, 10)
                    + calculatePercentOf(200000, 20)
                    + calculatePercentOf(taxableAmount - 500000, 30);
            generateOutput(taxableAmount, payableTax);
        }
    }

    private static float getTaxableAmount(TaxCalculator taxCalculator) {
        float houseRentExemption = calculatePercentOf(taxCalculator.houseLoan, 80);
//        System.out.println("houseRentExemption: " + houseRentExemption);
        float incomeExemption = calculatePercentOf(taxCalculator.income, 20);
//        System.out.println("incomeExemption: " + incomeExemption);
        float nonTaxableAmount = Math.min(houseRentExemption, incomeExemption);
//        System.out.println("nonTaxableAmount: " + nonTaxableAmount);
        float validInvestment = taxCalculator.investment > 100000 ? 100000 : taxCalculator.investment;
//        System.out.println("valid Investment: " + validInvestment);
        return taxCalculator.income - (nonTaxableAmount + validInvestment);
    }

    private static void generateOutput(float taxableAmount, float payableTaxAmount) {
        System.out.println("\n------------------------------------------------------");
        System.out.println("Tax Calculation Result");
        System.out.println("------------------------------------------------------");
        System.out.println("Taxable Amount:\t" + taxableAmount);
        System.out.println("Payable Tax Amount:\t" + payableTaxAmount);
    }

    private static void getInputDetails(TaxCalculator taxCalculator, Scanner sc) {
        System.out.println("======================================================");
        System.out.println("Tax Calculator");
        System.out.println("======================================================");
        System.out.println("\n------------------------------------------------------");
        System.out.println("Enter Person Details");
        System.out.println("------------------------------------------------------");
        System.out.println("Name:\t");
        taxCalculator.name = sc.nextLine();
        System.out.println("Birthdate [DD/MM/YYYY]:\t");
        try {
            taxCalculator.dob = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        catch (IllegalArgumentException e){
            System.out.println("Exception " + e);
        }
        System.out.println("Gender [M/F]:\t");
        taxCalculator.gender = sc.next().charAt(0);
        System.out.println("\n------------------------------------------------------");
        System.out.println("Enter Account Details");
        System.out.println("------------------------------------------------------");
        System.out.println("Income:\t");
        taxCalculator.income = sc.nextFloat();
        System.out.println("Investment:\t");
        taxCalculator.investment = sc.nextFloat();
        System.out.println("House Loan / Rent:\t");
        taxCalculator.houseLoan = sc.nextFloat();
        sc.nextLine();
    }



}
