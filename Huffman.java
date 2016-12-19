/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Taylor
 */
public class Huffman {
    static String [] encodeKeys = new String [256];
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String line="";
        ArrayList <Node> al = new ArrayList<Node>();
        int[] frequency = new int[256];
        try {
            Scanner input = new Scanner(new File("C:\\Users\\Taylor\\Documents\\NetBeansProjects\\Huffman\\src\\huffman\\characters.txt"));

            while (input.hasNextLine()) {
                line = input.nextLine();
            }
            
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Gets the ascii integer number and increments every time the value is the same
        for (int i = 0; i < line.length(); i++) 
            frequency [(int)line.charAt(i)]++;
        for (int i = 0; i < frequency.length; i++) 
            if(frequency[i] > 0)
                al.add (new Node(null,null,((char) i),frequency[i],0));

        while (al.size()!=1){
            int smallest = Integer.MAX_VALUE;
            int smallestID=0;
            int secondSmallest = Integer.MAX_VALUE;
            int secondSmallestID=0;
            for (int i = 0; i < al.size(); i++) {
                if(al.get(i).getFreq()==smallest){
                    secondSmallest = smallest;
                    secondSmallestID = i;
                } else if (al.get(i).getFreq() < smallest) {
                    secondSmallest = smallest;
                    secondSmallestID = smallestID;
                    smallest = al.get(i).getFreq();
                    smallestID = i;
                } else if (al.get(i).getFreq() < secondSmallest) {
                    secondSmallest = al.get(i).getFreq();
                    secondSmallestID = i;
                }
            }
            
            int two = al.get(secondSmallestID).getHeight();
            int one = al.get(smallestID).getHeight();
            if(secondSmallest == smallest){
                if(two > one)
                    al.add (new Node(al.get(smallestID),al.get(secondSmallestID),'>',(smallest+secondSmallest),Math.max(one, two)+1));
                else
                    al.add (new Node(al.get(secondSmallestID),al.get(smallestID),'>',(smallest+secondSmallest),Math.max(one, two)+1));
            }
            else{
                 al.add (new Node(al.get(secondSmallestID),al.get(smallestID),'>',(smallest+secondSmallest),Math.max(one, two)+1));
            }
            if(smallestID > secondSmallestID){
                al.remove(smallestID);
                al.remove(secondSmallestID);
            }
            else{
                al.remove(secondSmallestID);
                al.remove(smallestID);
            }
        }
        printLeafNodes(al.get(0),"");
        String result="";
        for (int i = 0; i < line.length(); i++) {
            result = result + encodeKeys [(int)line.charAt(i)];
        }
        System.out.println("RESULT:   " + result);
    }
    public static void printLeafNodes(Node t, String s)
    {
        if(t == null)       
            return;
        if(t.getLeft() == null && t.getLeft()==null){
            t.setCode(s);
            System.out.println("Value: " + t.getVal() + "  Code: " + s); 
            encodeKeys [(int)t.getVal()] = s;
        }
        printLeafNodes(t.left, s+"0"); 
        printLeafNodes(t.right, s+"1");      
    }
    
    private static class Node {
        private char val;
        private int freq;
        private Node left;
        private Node right;
        private String code;
        private int height;
    
        public Node (Node left, Node right, char val, int freq, int height) {
            this.left = left;
            this.right = right;
            this.val = val;
            this.freq = freq;
            code = "";
            this.height = height;
        }

        public char getVal() {
            return val;
        }

        public int getFreq() {
            return freq;
        } 
        public Node getLeft() {
            return left;
        } 
        public Node getRight() {
            return right;
        } 

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = this.code + code ;
        }
        public int getHeight(){
            return height;
        }
    }
}

