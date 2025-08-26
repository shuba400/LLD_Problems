package FileSystem;

public class FileSystemController {
    public void run(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.createFile("a.txt");
        fileSystem.createDirectory("dir0");
        fileSystem.createDirectory("dir0/patha");
        fileSystem.createDirectory("dir0/pathb");
        fileSystem.createFile("dir0/patha/b.txt");
        fileSystem.createFile("dir0/pathc/b.txt");
        fileSystem.createDirectory("dir0/pathc");
        fileSystem.createFile("dir0/pathc/b.txt");
        fileSystem.printFileStructure();
    }
}
