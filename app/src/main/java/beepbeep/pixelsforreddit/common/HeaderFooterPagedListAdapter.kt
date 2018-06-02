package beepbeep.pixelsforreddit.common

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup


abstract class HeaderFooterPagedListAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {
    open var hasHeader: Boolean = false
    open var hasFooter: Boolean = false

    fun HeaderFooterAdapter(hasHeader: Boolean, hasFooter: Boolean) {
        this.hasHeader = hasHeader
        this.hasFooter = hasFooter
    }

    fun showHeader() {
        hasHeader = true
        notifyItemInserted(0)
    }

    fun hideHeader() {
        hasHeader = false
        notifyItemRemoved(0)
    }

    fun showFooter() {
        hasFooter = true
        notifyItemInserted(itemCount)
    }

    fun hideFooter() {
        hasFooter = false
        notifyItemRemoved(itemCount)
    }

    fun hasHeader(): Boolean {
        return hasHeader
    }

    fun hasFooter(): Boolean {
        return hasFooter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (hasHeader && hasFooter) {
            if (viewType == ViewType.HEADER) {
                val header = onCreateHeaderViewHolder(parent)
                return header
                        ?: throw IllegalStateException("onCreateHeaderViewHolder() is returning null, it must be overridden and cannot return null")
            } else if (viewType == ViewType.FOOTER) {
                val footer = onCreateFooterViewHolder(parent)
                return footer
                        ?: throw IllegalStateException("onCreateFooterViewHolder() is returning null, it must be overridden and cannot return null")
            } else {
                return onCreateRealViewHolder(parent, viewType)
            }
        } else if (hasHeader && !hasFooter) {
            val header = onCreateHeaderViewHolder(parent)
            return if (viewType == ViewType.HEADER) {
                header
                        ?: throw IllegalStateException("onCreateHeaderViewHolder() is returning null, it must be overridden and cannot return null")
            } else {
                onCreateRealViewHolder(parent, viewType)
            }
        } else if (!hasHeader && hasFooter) {
            val footer = onCreateFooterViewHolder(parent)
            return if (viewType == ViewType.FOOTER) {
                footer
                        ?: throw IllegalStateException("onCreateFooterViewHolder() is returning null, it must be overridden and cannot return null")
            } else {
                onCreateRealViewHolder(parent, viewType)
            }
        } else {
            return onCreateRealViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (hasHeader && hasFooter) {

            if (position == 0) {
                onBindHeaderViewHolder(holder)
            } else if (position == itemCount - 1) { // footer
                onBindFooterViewHolder(holder)
            } else {
                onBindRealViewHolder(holder, position - 1)
            }
        } else if (hasHeader && !hasFooter) {
            if (position == 0) {
                onBindHeaderViewHolder(holder)
            } else {
                onBindRealViewHolder(holder, position - 1)
            }
        } else if (!hasHeader && hasFooter) {
            if (position == itemCount - 1) { // footer
                onBindFooterViewHolder(holder)
            } else {
                onBindRealViewHolder(holder, position)
            }
        } else {
            onBindRealViewHolder(holder, position)
        }
    }

    /*********
     * ViewType
     */
    private object ViewType {

        val HEADER = 999991
        val FOOTER = 999992
    }

    abstract fun getRealItemViewType(position: Int): Int

    override fun getItemViewType(position: Int): Int {
        return if (hasHeader && hasFooter) {
            if (position == 0) {
                ViewType.HEADER
            } else if (position == itemCount - 1) { // footer
                ViewType.FOOTER
            } else {
                getRealItemViewType(position - 1) // shift by header
            }

        } else if (hasHeader && !hasFooter) {
            if (position == 0) {
                ViewType.HEADER
            } else {
                getRealItemViewType(position - 1) // shift by header
            }
        } else if (!hasHeader && hasFooter) {
            if (position == itemCount - 1) { // footer
                ViewType.FOOTER
            } else {
                getRealItemViewType(position)
            }
        } else {
            getRealItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        return if (hasHeader && hasFooter) {
            2 + super.getItemCount()
        } else if (hasHeader || hasFooter) {
            1 + super.getItemCount()
        } else {
            super.getItemCount()
        }
    }

    fun notifyRealItemRangeInserted(positionStart: Int, itemCount: Int) {
        if (hasHeader) {
            notifyItemRangeInserted(positionStart + 1, itemCount)
        } else {
            notifyItemRangeInserted(positionStart, itemCount)
        }
    }

    fun notifyRealItemChanged(position: Int) {
        val realPosition = position + if (hasHeader) 1 else 0
        notifyItemChanged(realPosition)
    }

    /****************
     * Real children
     */
    //abstract fun getRealItemCount(): Int

    abstract fun onCreateRealViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindRealViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    /**********
     * Header
     */
    open fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    open fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {

    }

    /**********
     * Footer
     */
    open fun onCreateFooterViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    open fun onBindFooterViewHolder(holder: RecyclerView.ViewHolder) {

    }
}