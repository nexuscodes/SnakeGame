/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

/**
 *
 * @author Nexus
 */
public class UseMe {
    
    public static void main(String[] args) {
        LoadingWindow load = new LoadingWindow();
        load.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            
        }
        load.dispose();
        new Snake().setVisible(true);
    }
}
