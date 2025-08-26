package FileSystem.FileNode;

import java.time.LocalDateTime;

public abstract class SystemNode {
    public final LocalDateTime createdDate;
    public final boolean isDirectory;
    public final String name;
    public final SystemNode parentSystemNode;

    public SystemNode(String name,boolean isDirectory,SystemNode systemNode){
        this.createdDate = LocalDateTime.now();
        this.isDirectory = isDirectory;
        this.name = name;
        this.parentSystemNode = systemNode;
    }

    public abstract int getNumberOfChildren();
}
