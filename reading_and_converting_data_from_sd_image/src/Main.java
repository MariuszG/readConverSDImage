import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        String calSt = cal.getTime().toString().replaceAll("\\s+", "_");
        final String dirPath = "./imgSD/backup_my_sdcard" + calSt;
        final String fileName = "/backup_my_sdcard_" + calSt;
        new File(dirPath).mkdir();

        final String inputFile = dirPath + fileName + ".img";
        final String outputFile = dirPath + fileName + ".txt";
        final String command = "dd if=/dev/disk2 of=" + inputFile +  " bs=512";

        final int pseudorandomByteLength = 8;
        final int processedBytes = 1024;

        StringBuilder sb = new StringBuilder();
        readRawDataFromSDImage rawDataReader = new readRawDataFromSDImage();




        Runtime r = Runtime.getRuntime();
        try {
            Scanner scan = new Scanner(System.in);
            boolean nCardInside = true;
            System.out.println("Tworzenie obrazu karty SD");
            System.out.println("Proszę o włożenie nośnika SD do czytnika");
            while (nCardInside) {
                System.out.println("Czy umieszczono nośnik w slocie? t/n");
                if (scan.next().equals("t")) {
                    nCardInside = false;
                }
            }


            System.out.println("Rozpoczynam proces tworzenia obrazu karty SD!\nGenerowanie obrazu trwa około 5min.\nProszę nie wyjmować nośnika!");
//            Process p = r.exec("dd if=/dev/disk2 of=backup.my.sdcard.img bs=512");

//            Process p = r.exec("dd if=/dev/disk2 of=" + inputFile +  " bs=512");
            Process p = r.exec(command);
            p.waitFor();
            System.out.println("Process tworzenia obrazu karty SD zakończony!");

        } catch(IOException e) {
            System.out.println("IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        byte[] outputByteTable = readRawDataFromSDImage.readImageFile(inputFile);



        try {
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFile));
            try {

//        for(int i = 0; i < outputByteTable.length; i++){
                int j = 0;
//                for (int i = 0; i < processedBytes; i++) {
                for (int i = 0; i < outputByteTable.length; i++) {
                    System.out.println("#" + i + " wartość: " + String.format("%02X", outputByteTable[i]));
                    if (j < pseudorandomByteLength){
                        sb.append(String.format("%02X ", outputByteTable[i]));
                        j++;
                    } else {
                        outputWriter.write(sb.toString());
                        outputWriter.write("\n");
                        sb.setLength(0);
                        j = 0;
                    }
                }

            } finally {
                outputWriter.close();

            }
        } catch (IOException e){
            System.out.println("IOException");
        }
    }
}
