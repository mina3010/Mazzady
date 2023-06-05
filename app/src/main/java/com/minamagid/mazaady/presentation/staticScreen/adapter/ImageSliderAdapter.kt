package  com.minamagid.mazaady.presentation.staticScreen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.minamagid.mazaady.R

class ImageSliderAdapter(private val mContext: Context, private val mImageResources: IntArray) :
    PagerAdapter() {

    private val dots = arrayListOf<ImageView>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_image_slide, container, false)

        val imageView: ImageView = view.findViewById(R.id.imageView)
        imageView.setImageResource(mImageResources[position])

        container.addView(view)

        // Create and add a dot indicator
        val dot = inflater.inflate(R.layout.layout_indicator_dot, container, false) as ImageView
        dots.add(dot)
        container.addView(dot)
        updateDotIndicator(position)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
        dots.removeAt(position)
        container.removeViewAt(position)
    }


    override fun getCount(): Int {
        return mImageResources.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    private fun updateDotIndicator(currentPosition: Int) {
        for (i in dots.indices) {
            dots[i].setImageResource(if (i == currentPosition) R.drawable.dot_selected else R.drawable.dot_unselected)
        }
    }

    // Rest of the adapter implementation...
}

