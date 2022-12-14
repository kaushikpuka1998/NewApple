package com.kgstrivers.myapplication.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Toast
import com.kgstrivers.myapplication.Models.Opn
import com.kgstrivers.myapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.childlayout.view.*
import kotlinx.android.synthetic.main.grouplayout.view.*

class CustomExpandableListAdapter internal constructor(
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, List<Opn>>
) : BaseExpandableListAdapter(){
    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return this.dataList[this.titleList[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.titleList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.dataList[this.titleList[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val listTitle = getGroup(groupPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.grouplayout, null)
        }
        val listTitleTextView = convertView!!.facility
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convertView
    }

    @SuppressLint("DiscouragedApi")
    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertview = convertView
        val expandedListText:Opn = getChild(groupPosition,childPosition) as Opn
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertview = layoutInflater.inflate(R.layout.childlayout, null)
        }
        val expandedListTextView = convertview!!.childtextView
        val iconview  = convertview!!.iconview

        expandedListTextView.text = expandedListText.name

        var imageResource:Int
        if(expandedListText.icon != "no-room")
        {
            val k = (expandedListText.icon+"3x")

            imageResource = context.getResources().getIdentifier("drawable/"+k, null, context.getPackageName());

        }
        else{
            val k = ("no_room"+"3x")

            imageResource = context.resources.getIdentifier(
                "@drawable/" + k.replace(".png", ""),
                null,
                context.packageName
            )
        }
      iconview.setImageResource(imageResource)
        convertview.setOnClickListener {
            Toast.makeText(context,"Clicked:"+expandedListTextView.text,Toast.LENGTH_SHORT).show()
        }

        return convertview
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

}