package com.examen.examenandroid.medioPago;

import com.examen.examenandroid.MontoFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MedioPagoFragmentTest {

    @Mock
    MontoFragment fragment;

    @Test
    public void loadData() {

        fragment = new MontoFragment();

    }
}