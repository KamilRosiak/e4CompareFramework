package de.tu_bs.cs.isf.e4cf.core.compare.remote.util;

import com.google.gson.InstanceCreator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import java.lang.reflect.Type;

public class TreeInstanceCreator implements InstanceCreator<Tree> {
    @Override
    public Tree createInstance(Type type) {
        return new TreeImpl("");
    }
}

