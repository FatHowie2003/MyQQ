import java.util.*;

public class DijkstraRouting {
    // 定义一个常量表示无穷大，用于初始化距离数组
    private static final int INF = Integer.MAX_VALUE;

    // 学号数组
    private static final int[] X = {2, 1, 2, 7, 1, 2, 0, 3};

    public static void main(String[] args) {
        // 根据学号生成链路代价矩阵
        int[][] graph = generateGraph(X);

        // 对每个节点运行Dijkstra算法
        for (int i = 0; i < 8; i++) {
            dijkstra(graph, i);
            System.out.println("\n");
        }
    }

    // 根据学号生成链路代价矩阵
    private static int[][] generateGraph(int[] X) {
        int n = X.length; // 节点数量
        int[][] graph = new int[n][n]; // 初始化图的矩阵表示

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    // 对角线上的元素表示自身到自身的距离为0
                    graph[i][j] = 0;
                } else {
                    // 计算代价，根据学号数组计算代价
                    int cost = X[(i + j) % X.length];
                    // 如果代价为0，将其设为10，避免无效边
                    graph[i][j] = (cost == 0) ? 10 : cost;
                }
            }
        }
        return graph; // 返回生成的图的矩阵表示
    }

    // 实现Dijkstra算法
    private static void dijkstra(int[][] graph, int src) {
        int n = graph.length; // 节点数量
        int[] dist = new int[n]; // 存储从源节点到每个节点的最短距离
        boolean[] visited = new boolean[n]; // 标记节点是否已访问
        int[] prev = new int[n]; // 存储路径中的前一个节点

        // 初始化距离数组，将所有距离设为无穷大
        Arrays.fill(dist, INF);
        // 初始化已访问数组，将所有节点标记为未访问
        Arrays.fill(visited, false);
        // 初始化前一个节点数组，将所有前一个节点设为-1（表示没有前驱）
        Arrays.fill(prev, -1);

        // 源节点到自身的距离为0
        dist[src] = 0;

        // 循环遍历所有节点，寻找最短路径
        for (int i = 0; i < n - 1; i++) {
            // 选择距离源节点最近且未访问的节点
            int u = selectMinVertex(dist, visited);
            // 标记该节点为已访问
            visited[u] = true;

            // 更新从源节点到其他节点的距离
            for (int v = 0; v < n; v++) {
                // 如果节点v未访问，且存在从u到v的边，且通过u节点到v的距离小于当前记录的距离，则更新距离
                if (!visited[v] && graph[u][v] != 0 && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v]; // 更新距离
                    prev[v] = u; // 更新前驱节点
                }
            }
        }

        // 打印结果，包括每个节点的最短距离和路径
        printSolution(dist, prev, src);
    }

    // 选择距离最小且未访问的节点
    private static int selectMinVertex(int[] dist, boolean[] visited) {
        int min = INF; // 初始化最小值为无穷大
        int minIndex = -1; // 初始化最小值对应的索引为-1

        // 遍历所有节点，选择距离最小且未访问的节点
        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex; // 返回距离最小且未访问的节点索引
    }

    // 打印结果
    private static void printSolution(int[] dist, int[] prev, int src) {
        System.out.println("Node\tDistance from Source\tPath");

        for (int i = 0; i < dist.length; i++) {
            if (i != src) {
                System.out.print("\n" + src + " -> ");
                System.out.print(i + "\t\t\t ");
                System.out.print(dist[i] + "\t\t\t ");
                printPath(prev, i);
            }
        }
    }

    // 打印路径
    private static void printPath(int[] prev, int i) {
        if (i == -1) {
            return;
        }
        printPath(prev, prev[i]);
        System.out.print(i + " ");
    }
}
