package com.example.temp.scan;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.ViewHolder> {

    private List<UsePDF> mPDFList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pdf_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.PDFView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                UsePDF usePDF = mPDFList.get(position);
                Intent intent = new Intent(view.getContext(),ViewPDFActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UsePDF usePDF = mPDFList.get(position);
        holder.PDFImage.setImageResource(usePDF.getImageId());
        holder.PDFName.setText(usePDF.getName());
        holder.PDFContent.setText(usePDF.getContent());
        holder.PDFDate.setText(usePDF.getDate());
        holder.PDFAuthor.setText(usePDF.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mPDFList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View PDFView;
        ImageView PDFImage;
        TextView PDFName;
        TextView PDFContent;
        TextView PDFDate;
        TextView PDFAuthor;

        public ViewHolder(View view) {
            super(view);
            PDFView = view;
            PDFImage = (ImageView) view.findViewById(R.id.pdf_image);
            PDFName = (TextView) view.findViewById(R.id.pdf_name);
            PDFContent = (TextView) view.findViewById(R.id.pdf_content);
            PDFDate = (TextView) view.findViewById(R.id.pdf_time);
            PDFAuthor = (TextView) view.findViewById(R.id.pdf_author);
        }
    }

    public PDFAdapter(List<UsePDF>PDFList) {
        mPDFList = PDFList;
    }


}
