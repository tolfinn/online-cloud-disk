package com.example.clouddisk.util;

import com.example.clouddisk.entity.File;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FolderTree {

    /**
     *
     * @param rootPath 用户跟目录
     * @param dirs  从数据库查询道德所有文件夹
     * @return
     */
    public static List<Node> getFolderTree(String rootPath,List<File> dirs) {
        List<Node> res = new ArrayList<>();
        //添加根节点
        Node root = new Node(rootPath,new ArrayList<Node>());
        Queue<Node> queue = new LinkedList<>();
        res.add(root);
        queue.add(root);
        boolean [] mark = new boolean[dirs.size()];
        findNode(queue, mark, dirs);
        return res;
    }

    public static void findNode(Queue<Node> queue, boolean []mark, List<File> dirs) {
        while(!queue.isEmpty()) {
            Node node = queue.poll();
            String parentPath = node.getLabel();
            for(int i = 0; i < dirs.size(); ++i) {
                if(mark[i]) continue;
                File file = dirs.get(i);
                if(file.getParentDir().equals(parentPath)) {
                    mark[i] = true;
                    Node childNode = new Node(file.getFileId(),new ArrayList<Node>());
                    node.children.add(childNode);
                    queue.add(childNode);
                }
            }
        }
    }
}
