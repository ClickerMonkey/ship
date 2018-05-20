package com.philsprojects.chart.data;


public interface DatatableListener 
{

    public void dataRemoved(Datatable table, Dataset set, int setIndex);

    public void dataAdded(Datatable table, Dataset set, int setIndex);

    public void datasetReset(Datatable table);

}
