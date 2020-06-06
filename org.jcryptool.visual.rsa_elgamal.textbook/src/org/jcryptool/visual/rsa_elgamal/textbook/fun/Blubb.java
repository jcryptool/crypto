package org.jcryptool.visual.rsa_elgamal.textbook.fun;

import org.derive4j.Data;
import org.derive4j.Derive;
import org.derive4j.Instances;

import fj.Equal;
import fj.Hash;
import fj.Ord;
import fj.Show;

@Data(@Derive(@Instances({ Show.class, Hash.class, Equal.class, Ord.class })))
public abstract class Blubb {

  public abstract <R> R match(Cases<R> cases);

  interface Cases<R> {
    R Address(int number, String street);
  }

  
  public static void main(String[] args) {
	
  }
}