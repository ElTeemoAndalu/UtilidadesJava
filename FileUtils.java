package utilidades;

import java.io.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class FileUtils {

    //Pa obtener un array de archivos que han sido filtrados con la clase de abajo
    public static File[] filterFiles(File directorio, String extensionBuscada){
        return directorio.listFiles(new MyFileNameFilter(extensionBuscada));
    }

    public static class MyFileNameFilter implements FilenameFilter {

        private String ext;

        public MyFileNameFilter(String ext) {
            this.ext = ext.toLowerCase();
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }
    }


    //Mira el nombre de la función vvvvvvvvvvvv
    public static void copyFileToDirectory(File archivoOrigen, File directorioDestino){
        BufferedReader buffR;
        BufferedWriter buffW;
        File newFile;
        String line;

        if(archivoOrigen.exists() && directorioDestino.exists()){
            if(directorioDestino.isDirectory()){
                newFile = new File(directorioDestino.getAbsolutePath(), archivoOrigen.getName());
                try {

                    newFile.createNewFile();
                    buffR = newBuffReader(archivoOrigen);
                    buffW = newBuffWriter(newFile);

                    while ((line = buffR.readLine()) != null) {
                        buffW.write(line);
                        buffW.newLine();
                    }

                    buffR.close();
                    buffW.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{

            }
        }

    }

    //------------------------------------------------------------BUFFERED------------------------------------------------------------------------------//

    //Pa crear un BufferedWriter
    public static BufferedWriter newBuffWriter(File archivo){

        try {
            return new BufferedWriter(new FileWriter(archivo));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Pa crear un BufferedReader
    public static BufferedReader newBuffReader(File archivo){

        try {
            return new BufferedReader(new FileReader(archivo));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------

    //-------------------------------------------------------------DATASTREAM--------------------------------------------------------------------
    //Pa crear un DataOutputStream (EL DE ESCRIBIR)
    public static DataOutputStream newDataOutStrm(File archivo){

        try {
            return new DataOutputStream(new FileOutputStream(archivo));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Pa crear un DataInputStream (EL DE LEER)
    public static DataInputStream newDataInStrm(File archivo){

        try {
            return new DataInputStream(new FileInputStream(archivo));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    //Para arreglar las cabeceras al escribir con serializacion de objetos en un archivo
    // en el que ya se ha escrito previamente de esta misma forma
    public class MyHeaderFix extends ObjectOutputStream {

        public MyHeaderFix(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
        }
    }
}
