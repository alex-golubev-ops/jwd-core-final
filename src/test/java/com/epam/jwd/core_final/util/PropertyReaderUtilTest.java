package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import java.util.Optional;

import static org.junit.Assert.*;

public class PropertyReaderUtilTest {
    private PropertyReaderUtil propertyReaderUtil;
    @Before
    public void init(){
       propertyReaderUtil = PropertyReaderUtil.getInstance();
    }


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void getPropertyTest1() {
        Optional<String> inputRootDir = propertyReaderUtil.getProperty("crewFileName");
        Assert.assertEquals(inputRootDir.get(),"crew");
    }

    @Test(expected = NullPointerException.class)
    public void getPropertyTest3() {
        Optional<String> inputRootDir = propertyReaderUtil.getProperty("crew");
    }
}