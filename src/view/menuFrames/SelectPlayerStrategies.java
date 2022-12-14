package view.menuFrames;

import controller.editor.ReadingFiles;
import controller.game.ServerController;
import controller.game.Tournament;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class implements user interface for player strategies
 *
 * @author pazim
 * @version 1.0
 */
public class SelectPlayerStrategies {

    private JButton ok_button;
    private JFrame frame;

    ArrayList<JLabel> jlabel1 = new ArrayList<>();
    ArrayList<JComboBox> jcombo_array = new ArrayList<>();

    String[] single = {"Human", "Aggressive", "Benevolent", "Random", "Cheater"};
    String[] tournament = {"Aggressive", "Benevolent", "Random", "Cheater"};

    /**
     * Constructor for Select player strategies
     */
    public SelectPlayerStrategies() {
        setup();
        GetSelectedValue();
    }

    /**
     * Implements user interface for different player strategies
     */
    public void setup() {
        frame = new JFrame("Players Strategies");
        frame.setSize(500, ReadingFiles.NumberOfPlayers * 70 + 100);

        String[] list = null;
        if (GameStartWindow.GameMode == 2)
            list = tournament;
        else
            list = single;

        for (int i = 0, j = 10; i < ReadingFiles.NumberOfPlayers; i++, j += 50) {
            jlabel1.add(new JLabel("Player" + (i + 1) + ":"));
            jlabel1.get(i).setBounds(10, j, 250, 50);
            jcombo_array.add(new JComboBox(list));
            jcombo_array.get(i).setBounds(100, j + 15, 200, 20);
            frame.add(jcombo_array.get(i));
            frame.add(jlabel1.get(i));
            jcombo_array.get(i).setVisible(true);
            jlabel1.get(i).setVisible(true);
        }
        ok_button = new JButton("OK");
        ok_button.setBounds(290, ReadingFiles.NumberOfPlayers * 60, 100, 30);
        frame.add(ok_button);
        ok_button.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    /**
     * This method save the strategies that have been selected, control errors
     * and starts the tournament/singlemode game.
     */
    public void GetSelectedValue() {
        ok_button.addActionListener(event -> {
                //Save strategies selected
                for (int i = 0, j = 10; i < ReadingFiles.NumberOfPlayers; i++, j += 50) {
                    ReadingFiles.strategy_selected.add((String) jcombo_array.get(i).getSelectedItem());
                }
                //Start Tournament
                if (GameStartWindow.GameMode == 2) {
                    frame.dispose();
                    Tournament temp = new Tournament();
                //Start Singlemode game
                } else {
                    try {
                        if (ReadingFiles.strategy_selected.contains("Human")) {
                            frame.dispose();
                            ServerController control = new ServerController();
                            control.Function();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "There Should be Atleast One Human Player. \n For all Computer Select Tournament Mode \n Select At least One Player");
                            ReadingFiles.strategy_selected.clear();
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
        });
    }

    /**
     * @return ArrayList</String> of strategies that have been selected
     */
    public static ArrayList<String> getStrategies() {
        return ReadingFiles.strategy_selected;
    }

}
