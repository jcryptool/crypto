package org.jcryptool.visual.rsa_elgamal.textbook.fun;

import org.derive4j.Data;
import org.derive4j.Derive;
import org.derive4j.Instances;

import fj.Equal;
import fj.Hash;
import fj.Ord;
import fj.Show;

@Data
public abstract class Blubb {

  public abstract <R> R match(Cases<R> cases);

  interface Cases<R> {
    R Mycase(int number, String street);
  }

  public static void main(String[] args) {
	  
  }
}