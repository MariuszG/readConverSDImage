import java.io.*;

public class readRawDataFromSDImage {

   static byte[] readImageFile(String inputFile){
        System.out.println("Czytam obraz: " + inputFile);

        File file = new File(inputFile);
        System.out.println("Wielkość obrazu: " + file.length());
        byte[] result = new byte[(int) file.length()];

        try{
            InputStream inputStream = null;
            try{
                int totalReadBytes = 0;
                inputStream = new BufferedInputStream(new FileInputStream(file));

                while(totalReadBytes < result.length){
                    int bytesRemarings = result.length - totalReadBytes;

                    int bytesRead = inputStream.read(result, totalReadBytes, bytesRemarings);

                    if(bytesRead > 0){
                        totalReadBytes = totalReadBytes + bytesRead;
                    }
                }
                System.out.println("Ilość wczytanych bytów: " + totalReadBytes);
            } finally {
                System.out.println("Zamknięcie strumienia wejściowego");
                inputStream.close();
            }
        } catch (FileNotFoundException f) {
            System.out.println("Plik nie znaleziony!");
        } catch (IOException e ) {
            System.out.println("IOException");
        }


        return result;
    }



}
