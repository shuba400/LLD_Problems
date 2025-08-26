package FileSystem;

import FileSystem.FileNode.DirectoryNode;
import FileSystem.FileNode.FileNode;
import FileSystem.FileNode.SystemNode;

import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    final DirectoryNode rootSystemNode;
    final static String deliminator = "/";

    public FileSystem(){
        rootSystemNode = new DirectoryNode("root",null);
    }


    public boolean createDirectory(String path){
        return addPath(path,true);
    }

    public boolean createFile(String path){
        boolean done = addPath(path,false);
        if(done){
            System.out.printf("Able to create file %s\n",path);
        }
        else {
            System.out.print("Not able to create file\n");
        }
        return done;
    }

    public FileNode getFile(String path){
        return (FileNode) getPath(path,false);
    }

    public DirectoryNode getDirectory(String path){
        return (DirectoryNode) getPath(path,true);
    }

    public boolean removeFile(String path){
        return removePath(path,false);
    }

    public boolean removeDirectory(String path){
        return removePath(path,true);
    }


    /*
    a
    ├─d
      |-6
    ├────
    |_a
    a
    |-b
    |-c
      |-e
      |-f
      |-e
     */
    public void printFileStructure(){
        printFileStructure(rootSystemNode,0);
    }

    private void printFileStructure(SystemNode systemNode,int len){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ".repeat(Math.max(0, len - 2)));
        if(len > 0){
            stringBuilder.append('|');
            stringBuilder.append('-');
        }
        stringBuilder.append(systemNode.name);
        if(!systemNode.isDirectory) stringBuilder.append('*');
        System.out.println(stringBuilder);
        if(!systemNode.isDirectory){
            return;
        }
        DirectoryNode directoryNode = (DirectoryNode) systemNode;
        for(SystemNode childrenNode : directoryNode.childrenNodes.values()){
            printFileStructure(childrenNode,len + 2);
        }
    }

    private SystemNode getPath(String path,boolean isDirectory){
        ArrayList<String> pathArray = new ArrayList<>(List.of(path.split(deliminator)));
        return getSystemNode(pathArray,isDirectory);
    }

    private boolean addPath(String path,boolean isDirectory){
        ArrayList<String> pathArray = new ArrayList<>(List.of(path.split(deliminator)));
        return addSystemNode(pathArray,isDirectory);
    }


    private boolean removePath(String path,boolean isDirectory){
        ArrayList<String> pathArray = new ArrayList<>(List.of(path.split(deliminator)));
        return removeSystemNode(pathArray,isDirectory);
    }

    private boolean addSystemNode(List<String> path,boolean isDirectory){
        DirectoryNode parentNode = rootSystemNode;
        String nodeName = path.removeLast();
        if(!path.isEmpty()) {
            parentNode = (DirectoryNode) getSystemNode(path, true);
        }
        if(parentNode == null){
            return false;
        }

        if(parentNode.childrenNodes.containsKey(nodeName)){
            return false;
        }
        final SystemNode newSystemNode;
        if(isDirectory){
            newSystemNode = new DirectoryNode(nodeName,parentNode);
        } else {
            newSystemNode = new FileNode(nodeName,parentNode);
        }
        parentNode.childrenNodes.put(newSystemNode.name,newSystemNode);
        return true;
    }

    private SystemNode getSystemNode(List<String> path,boolean isDirectory){
        DirectoryNode currentNode = rootSystemNode;
        int ptr = 0;
        while (ptr <  path.size() - 1){
            SystemNode nxtNode =  currentNode.childrenNodes.get(path.get(ptr));
            if(nxtNode == null || !nxtNode.isDirectory){
                return null;
            }
            currentNode = (DirectoryNode) nxtNode;
            ptr++;
        }
        String nodeName = path.getLast();
        if(isDirectory){
            return currentNode.childrenNodes.get(nodeName);
        }
        return currentNode.childrenNodes.get(nodeName);
    }


    private boolean removeSystemNode(List<String> path,boolean isDirectory){
        if (path.isEmpty()){
            return false;
        }
        SystemNode currentNode = getSystemNode(path,isDirectory);
        if(currentNode == null){
            return false;
        }
        DirectoryNode parentNode = (DirectoryNode) currentNode.parentSystemNode;
        parentNode.childrenNodes.remove(currentNode.name);

        currentNode = parentNode;
        while (currentNode != rootSystemNode){
            if(currentNode.getNumberOfChildren() > 0){
                break;
            }
            parentNode = (DirectoryNode) currentNode.parentSystemNode;
            parentNode.childrenNodes.remove(currentNode.name);
            currentNode = parentNode;
        }
        return true;
    }
}
