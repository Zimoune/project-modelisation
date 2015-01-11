/**
 * Cette classe sert a definir un JFloatSlider, utilise pour faire varier l'intensite de la puissance lumineuse de l'objet.
 */
package fr.minestate.bordel;

import java.awt.Color;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.UIDefaults;
import javax.swing.UIManager;


public class JFloatSlider extends JSlider
   {
	
     private final int SCALE = 10;
     public JFloatSlider(int position,float min, float max, float init, float tick)
     {
	 
    	 // Change l'aspect du slider
    	 UIDefaults defaults = UIManager.getDefaults();    
         defaults.put("Slider.altTrackColor", Color.blue);
         defaults.put("Slider.thumb", Color.blue);

       this.setPaintLabels(true);
       
       // On n'affiche pas les graduations (1 par 1)
       //this.setPaintTicks(true);

       setMinimum((int)(min*SCALE));
       setMaximum((int)(max*SCALE));
       this.setValue((int)(init*SCALE));
		this.setOrientation(position);
       Hashtable ht = new Hashtable();
       for (float i = min; i <= max; i+=tick)
       {
         JLabel l = new JLabel(""+i);
         // On n'affiche pas les graduations (0,1 par 0,1)
         // ht.put(new Integer((int)Math.rint(i*SCALE)), l);
       }
       //this.setLabelTable(ht);
       this.setMinorTickSpacing((int)(tick * SCALE / 4));
     //  this.setPaintTicks(true);
     }
     public float getFloatValue() { return (float)getValue()/(float)SCALE; }
   }