package utilidades;

import java.io.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
    public static void copyFileToDirectory(File fromFile, File toDirectory){
        BufferedReader buffR;
        BufferedWriter buffW;
        File newFile;
        String line;

        if(fromFile.exists() && toDirectory.exists()){
            if(toDirectory.isDirectory()){
                newFile = new File(toDirectory.getAbsolutePath(), fromFile.getName());
                try {

                    newFile.createNewFile();
                    buffR = newBuffReader(fromFile);
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
    public static BufferedWriter newBuffWriter(File file){

        try {
            return new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Pa crear un BufferedReader
    public static BufferedReader newBuffReader(File file){

        try {
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------

    //-------------------------------------------------------------DATASTREAM--------------------------------------------------------------------
    //Pa crear un DataOutputStream (EL DE ESCRIBIR)
    public static DataOutputStream newDataOutStrm(File file){

        try {
            return new DataOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Pa crear un DataInputStream (EL DE LEER)
    public static DataInputStream newDataInStrm(File file){

        try {
            return new DataInputStream(new FileInputStream(file));
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

    //Pa crear un ObjectOutputStream (EL DE ESCRIBIR)
    public static ObjectOutputStream newObjOutStrm(File file){

        try {
            return new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Pa crear un ObjectInputStream (EL DE LEER)
    public static ObjectInputStream newObjInpStrm(File file){

        try {
            return new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <O> List<O> readObjects(File file){
        List<O> list = new ArrayList<>();
        ObjectInputStream objInpStrm = newObjInpStrm(file);

        try {
            while (true) {
                O contacto = (O) objInpStrm.readObject();
                list.add(contacto);
            }

        } catch (EOFException eo) {
            eo.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            objInpStrm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public class ListForXML<O> {
        List<O> listForXML = new ArrayList<>();

        public ListForXML() {}

        public ListForXML(List<O> listForXML) {
            this.listForXML = listForXML;
        }

        public List<O> getListForXML() {
            return listForXML;
        }

        public void addElement(O elemento){
            listForXML.add(elemento);
        }
    }


}
