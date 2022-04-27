package hw3.hash;

import java.util.LinkedList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int N = oomages.size();
        LinkedList<Oomage>[] buckets = new LinkedList[M];
        for (int i = 0; i < M; i += 1) {
            buckets[i] = new LinkedList<>();
        }
        for (Oomage o : oomages) {
            int bucketIndex = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketIndex].add(o);
        }
        for (int i = 0; i < M; i += 1) {
            int n = buckets[i].size();
            if (n > N / 2.5 || n < N / 50.0) {
                return false;
            }
        }
        return true;
    }
}
