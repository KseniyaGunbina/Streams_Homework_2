import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String file, GameProgress gameProgress) throws IOException {
        // создание файла сохраненной игры
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

        public static void main(String[] args) throws IOException {

            GameProgress save1 = new GameProgress(1000, 4, 2, 4.6);
            GameProgress save2 = new GameProgress(800, 5, 3, 10.5);
            GameProgress save3 = new GameProgress(500, 6, 4, 26.8);

            Main.saveGame("c:/Games/savegames/save1.dat", save1);
            Main.saveGame("c:/Games/savegames/save2.dat", save2);
            Main.saveGame("c:/Games/savegames/save3.dat", save3);

            // архивирование файла
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("c:/Games/savegames/saves_zip.zip"));
                FileInputStream fis1 = new FileInputStream("c:/Games/savegames/save1.dat");
                FileInputStream fis2 = new FileInputStream("c:/Games/savegames/save2.dat");
                FileInputStream fis3 = new FileInputStream("c:/Games/savegames/save3.dat")) {
                ZipEntry ze1 = new ZipEntry("packet_save1.dat");
                ZipEntry ze2 = new ZipEntry("packet_save2.dat");
                ZipEntry ze3 = new ZipEntry("packet_save3.dat");
                zos.putNextEntry(ze1);
                zos.putNextEntry(ze2);
                zos.putNextEntry(ze3);
                byte[] buffer1 = new byte[fis1.available()];
                byte[] buffer2 = new byte[fis2.available()];
                byte[] buffer3 = new byte[fis3.available()];
                fis1.read(buffer1);
                zos.write(buffer1);
                fis1.read(buffer2);
                zos.write(buffer2);
                fis1.read(buffer3);
                zos.write(buffer3);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            Files.delete(Path.of("c:/Games/savegames/save1.dat"));
            Files.delete(Path.of("c:/Games/savegames/save2.dat"));
            Files.delete(Path.of("c:/Games/savegames/save3.dat"));

        }
    }
