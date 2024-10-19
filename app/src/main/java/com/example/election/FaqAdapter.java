package com.example.election;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class FaqAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> faqList;
    private HashMap<String, String> faqMap;

    public FaqAdapter(Context context, List<String> faqList, HashMap<String, String> faqMap) {
        this.context = context;
        this.faqList = faqList;
        this.faqMap = faqMap;
    }

    @Override
    public int getGroupCount() {
        return faqList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1; // Each question has only one answer
    }

    @Override
    public Object getGroup(int groupPosition) {
        return faqList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return faqMap.get(faqList.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String question = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, null);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(question);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String answer = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_2, null);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(answer);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
