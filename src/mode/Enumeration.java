package mode;

/**
 *
 * This class provides a vector enumeration facility. The enumeration is done 
 * in mso order.
 * 
 * @author Sergey Verlan
 * @version 1.0
 */
public class Enumeration {
  private int[] currPos;
  private int[] maxValues;
     
  
  /**
   * The constructor takes the vector of maximum values for the enumeration.
   * 
   * @param maxValues the maximal values vector
   */
  public Enumeration(int[] maxValues){
      this.maxValues = maxValues;
      currPos=new int[maxValues.length];
      for(int i=0;i<maxValues.length;i++)currPos[i]=0;
  }
  
  /**
   * Checks if there is one more step.
   * 
   * @return true if the enumeration is not yet ended.
   */
  public boolean hasNext(){
      for(int i=0;i<currPos.length;i++){
          if(currPos[i]<maxValues[i]) return true;
      }
      return false;
  }
  
  /**
   * Returns next enumeration values.
   * 
   * @return the next enumeration values
   */
  public int[] next(){
      int i=0;
      while(i<currPos.length && currPos[i]==maxValues[i]){
          currPos[i]=0;
          i++;
      }
      if (i<currPos.length) currPos[i]++;
      return currPos;
  }

//    @Override
//    public void remove() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
  
  
}
