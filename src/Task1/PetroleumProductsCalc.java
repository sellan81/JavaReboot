package Task1;

import java.util.ArrayList;
import java.util.Scanner;

public class PetroleumProductsCalc {
    public static  String[] inputDataArray = {"C100_1-100", "C200_1-120-1200", "C300_1-120-30", "C400_1-80-20", "C100_2-50", "C200_2-40-1000", "C300_2-200-45", "C400_2-10-20", "C100_3-10", "C200_3-170-1100", "C300_3-150-29", "C400_3-100-28", "C100_1-300", "C200_1-100-750", "C300_1-32-15"};
    public static final String delim = "_";
    public static ArrayList<DataCars> carsArr = new ArrayList<>();

    public static void main(String[] args) {
        prepareArr();

        System.out.println("Справка - /? , для выхода - exit");
        Scanner scanner = new Scanner(System.in);
        String inputStr = "null";

        while ( !inputStr.equals("EXIT") ) {
            inputStr = scanner.nextLine().toUpperCase();
            switch ( inputStr ) {
                case ("/?"):
                    System.out.println("full amount - рассчитать общую стоимость расходов на ГСМ");
                    System.out.println("each amount - рассчитать расходы на каждый класс авто");
                    System.out.println("max amount - тип авто имеющий наибольшую стоимость расходов");
                    System.out.println("min amount - тип авто имеющий наименьшую стоимость расходов");
                    System.out.println("getinfo TYPEAVTO - получить полную информация в разрезе типа авто с сортировкой (пример getinfo C100) ");
                    System.out.println("/? - справка по командам");
                    System.out.println("exit - выход");
                    break;
                case ("EXIT"):
                    break;
                case ("FULL AMOUNT"):
                    calcFullAmount();
                    break;
                case ("EACH AMOUNT"):
                    calcEachClassAmount(1);
                    break;
                case ("MAX AMOUNT"):
                    calcEachClassAmount(2);
                    break;
                case ("MIN AMOUNT"):
                    calcEachClassAmount(3);
                    break;
                case ("GETINFO"):
                    GetInfoByCarType();
                    break;
                default:
                    System.out.println("команда не распознана  /? - справка");
                    break;
            }
        }
    }

    private static void prepareArr(){
        for (String arr : inputDataArray) {
            arr = arr.replace("-", delim.toString());
            System.out.println(arr);
            DataCars oneCar = new DataCars(arr);
            carsArr.add(oneCar);
        }
    }

    private static double calcCostFuelOneCar(DataCars arr){
        return  arr.mileage * arr.GetConsumptionFuel100(arr.typeOfCar) * arr.GetFuelPrice(arr.typeOfCar) / 100;
    }

    private static double getMaxValue(double[] array){
        double maxValue = 0;
        for (double value : array) {
            if(value > maxValue)
                maxValue = value;
        }

        return  maxValue;
    }

    private static double getMinValue(double[] array){
        double minValue = array[0];
        for (double value : array) {
            if(value < minValue)
                minValue = value;
        }

        return  minValue;
    }


    private static void calcFullAmount(){
        double fullAmount = 0;
        for ( DataCars arr : carsArr ) {
            fullAmount += calcCostFuelOneCar(arr);
        }
        System.out.println("Общая стоимость расходов на ГСМ: " + String.format("%.2f", fullAmount) );
    }

    private static void calcEachClassAmount(int calcType){
        double[] eachAmount = new double[4];
        for ( DataCars arr : carsArr ) {
            switch (arr.typeOfCar){
                case ("C100"):
                    eachAmount[0] += calcCostFuelOneCar(arr);
                    break;
                case ("C200"):
                    eachAmount[1] += calcCostFuelOneCar(arr);
                    break;
                case ("C300"):
                    eachAmount[2] += calcCostFuelOneCar(arr);
                    break;
                case ("C400"):
                    eachAmount[3] += calcCostFuelOneCar(arr);
                    break;
                default:
                    System.out.println("(GetFuelPrice) Внимание! неизвестный тип авто " + arr.typeOfCar);
                    break;
            }
        }
        if (calcType == 1) {
            System.out.println("Cтоимость расходов на ГСМ: ");
            for (int i = 0; i < eachAmount.length; i++) {
                System.out.println("    C" + (i + 1) + "00 - " + String.format("%.2f", eachAmount[i]));
            }
        } else if(calcType == 2){
            double maxVal = getMaxValue(eachAmount);
            for (int i = 0; i < eachAmount.length; i++) {
                if (maxVal == eachAmount[i])
                    System.out.println("Тип авто имеющий наибольшую стоимость расходов    C" + (i + 1) + "00 - " + String.format("%.2f", maxVal));
            }
        } else if(calcType == 3){
            double minVal = getMinValue(eachAmount);
            for (int i = 0; i < eachAmount.length; i++) {
                if (minVal == eachAmount[i])
                    System.out.println("Тип авто имеющий наименьшую стоимость расходов    C" + (i + 1) + "00 - " + String.format("%.2f", minVal));
            }
        }
    }

    private static void sortArr(){
        
    }

    private static void GetInfoByCarType(){
        sortArr();
    }

    private static class DataCars {
        private String typeOfCar;
        private int namberCar;
        private double mileage;
        private String addParam;

        private DataCars(String inputStr) {
            String[] splitArr = inputStr.split(delim);
            this.typeOfCar = splitArr[0];
            this.namberCar = Integer.parseInt(splitArr[1]);
            this.mileage = Double.parseDouble(splitArr[2]);
            this.addParam = splitArr.length > 3 ? splitArr[3] : null;
        }

        private double GetConsumptionFuel100(String sTypeCar){
            switch (sTypeCar){
                case ("C100"):
                    return 46.1;
                case ("C200"):
                    return 48.90;
                case ("C300"):
                    return 47.50;
                case ("C400"):
                    return 48.90;
                default:
                    System.out.println("(GetConsumptionFuel100) Внимание! неизвестный тип авто " + sTypeCar);
                    return 0;
            }
        }

        private double GetFuelPrice(String sTypeCar){
            switch (sTypeCar){
                case ("C100"):
                    return 12.5;
                case ("C200"):
                    return 12;
                case ("C300"):
                    return 11.5;
                case ("C400"):
                    return 20;
                default:
                    System.out.println("(GetFuelPrice) Внимание! неизвестный тип авто " + sTypeCar);
                    return 0;
            }
        }
    }
}
