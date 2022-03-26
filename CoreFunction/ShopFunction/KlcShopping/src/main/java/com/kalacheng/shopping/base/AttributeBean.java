package com.kalacheng.shopping.base;

import android.widget.EditText;

import com.kalacheng.shopping.seller.adapter.AttributeValueAdapter;

public class AttributeBean {

    EditText editText;
    AttributeValueAdapter attributeValueAdapter;

    public AttributeBean(EditText editText, AttributeValueAdapter attributeValueAdapter) {
        this.editText = editText;
        this.attributeValueAdapter = attributeValueAdapter;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public AttributeValueAdapter getAttributeValueAdapter() {
        return attributeValueAdapter;
    }

    public void setAttributeValueAdapter(AttributeValueAdapter attributeValueAdapter) {
        this.attributeValueAdapter = attributeValueAdapter;
    }
}
