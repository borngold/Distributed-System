package cs5223.maze.client;

class ClientKeygen  
{  
  public String genKey()  
  {  
    final int PASSWORD_LENGTH = 8;  
    StringBuffer sb = new StringBuffer();  
    for (int x = 0; x < PASSWORD_LENGTH; x++)  
    {  
      sb.append((char)((int)(Math.random()*26)+97));  
    }  
    return sb.toString();  
  }  
    
} 
