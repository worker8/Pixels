package beepbeep.pixelsforredditx.common

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

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
        when {
            hasHeader && hasFooter -> return when (viewType) {
                ViewType.HEADER -> {
                    onCreateHeaderViewHolder(parent)
                        ?: throw IllegalStateException("onCreateHeaderViewHolder() is returning null, it must be overridden and cannot return null")
                }
                ViewType.FOOTER -> {
                    val footer = onCreateFooterViewHolder(parent)
                    footer
                        ?: throw IllegalStateException("onCreateFooterViewHolder() is returning null, it must be overridden and cannot return null")
                }
                else -> onCreateRealViewHolder(parent, viewType)
            }
            hasHeader && !hasFooter -> {
                val header = onCreateHeaderViewHolder(parent)
                return if (viewType == ViewType.HEADER) {
                    header
                        ?: throw IllegalStateException("onCreateHeaderViewHolder() is returning null, it must be overridden and cannot return null")
                } else {
                    onCreateRealViewHolder(parent, viewType)
                }
            }
            !hasHeader && hasFooter -> {
                val footer = onCreateFooterViewHolder(parent)
                return if (viewType == ViewType.FOOTER) {
                    footer
                        ?: throw IllegalStateException("onCreateFooterViewHolder() is returning null, it must be overridden and cannot return null")
                } else {
                    onCreateRealViewHolder(parent, viewType)
                }
            }
            else -> return onCreateRealViewHolder(parent, viewType)
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

    /**
     * override this method to return the Header ViewHolder
     */
    open fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? = null

    /**
     * override this method to bind the Header ViewHolder
     */
    open fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {

    }

    /**
     * override this method to return the Footer ViewHolder
     */
    open fun onCreateFooterViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? = null

    /**
     * override this method to bind the Footer ViewHolder
     */
    open fun onBindFooterViewHolder(holder: RecyclerView.ViewHolder) {

    }
}
