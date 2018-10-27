
package ejb;

import java.util.List;

import javax.ejb.Remote;

import data.Player;

@Remote
public interface PlayersEJBRemote {
 public void populate();
 public List<Player> playersTallerThan(float threshold);
}
