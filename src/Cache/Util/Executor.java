package Cache.Util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Executor<K> {
    private ExecutorService[] executors;
    int num_of_executors;

    public Executor(int num_of_executors){
        this.num_of_executors = num_of_executors;
        this.executors = new ExecutorService[num_of_executors];
        for(int i = 0; i < num_of_executors; i++){
            executors[i] = Executors.newSingleThreadExecutor();
        }

    }

    public<T> CompletableFuture<T> execute(K key, Supplier<T> func){
        final ExecutorService executor= getExecutor(key);
        return CompletableFuture.supplyAsync(func,executor);
    }

    public ExecutorService getExecutor(K key){
        return this.executors[key.hashCode() % num_of_executors];
    }

    public int getExecutorIndex(K key){
        return key.hashCode() % num_of_executors;
    }

    public void shutDown(){
        for(int i = 0; i < num_of_executors; i++){
            this.executors[i].shutdown();
        }
    }

}
