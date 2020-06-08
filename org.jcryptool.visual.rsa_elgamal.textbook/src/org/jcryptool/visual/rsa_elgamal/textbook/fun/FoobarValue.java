package org.jcryptool.visual.rsa_elgamal.textbook.fun;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.immutables.value.Value;

import fj.Equal;
import fj.Hash;
import fj.Ord;
import fj.Show;

@Value.Immutable
public abstract class FoobarValue {
	@Value.Parameter
  public abstract int foo();
	@Value.Parameter
  public abstract String bar();
  public abstract List<BigInteger> lol();
  public abstract List<Integer> buz();
  public abstract Set<Long> crux();
  public abstract Set<Long> crux2();
  
  public static void main(String[] args) {
	System.out.println(ImmutableFoobarValue.of(1, ""));

  }
}