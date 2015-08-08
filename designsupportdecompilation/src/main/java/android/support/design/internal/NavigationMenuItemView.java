package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
 import nz.xbc.designsupportdecompilation.R.attr;
 import nz.xbc.designsupportdecompilation.R.dimen;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.MenuView.ItemView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class NavigationMenuItemView extends TextView implements ItemView {
   private static final int[] CHECKED_STATE_SET = new int[]{16842912};
   private int mIconSize;
   private MenuItemImpl mItemData;
   private ColorStateList mIconTintList;

   public NavigationMenuItemView(Context context) {
      this(context, (AttributeSet)null);
   }

   public NavigationMenuItemView(Context context, AttributeSet attrs) {
      this(context, attrs, 0);
   }

   public NavigationMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      this.mIconSize = context.getResources().getDimensionPixelSize(dimen.navigation_icon_size);
   }

   public void initialize(MenuItemImpl itemData, int menuType) {
      this.mItemData = itemData;
      this.setVisibility(itemData.isVisible()?0:8);
      if(this.getBackground() == null) {
         this.setBackgroundDrawable(this.createDefaultBackground());
      }

      this.setCheckable(itemData.isCheckable());
      this.setChecked(itemData.isChecked());
      this.setEnabled(itemData.isEnabled());
      this.setTitle(itemData.getTitle());
      this.setIcon(itemData.getIcon());
   }

   private StateListDrawable createDefaultBackground() {
      TypedValue value = new TypedValue();
      if(this.getContext().getTheme().resolveAttribute(attr.colorControlHighlight, value, true)) {
         StateListDrawable drawable = new StateListDrawable();
         drawable.addState(CHECKED_STATE_SET, new ColorDrawable(value.data));
         drawable.addState(EMPTY_STATE_SET, new ColorDrawable(0));
         return drawable;
      } else {
         return null;
      }
   }

   public MenuItemImpl getItemData() {
      return this.mItemData;
   }

   public void setTitle(CharSequence title) {
      this.setText(title);
   }

   public void setCheckable(boolean checkable) {
      this.refreshDrawableState();
   }

   public void setChecked(boolean checked) {
      this.refreshDrawableState();
   }

   public void setShortcut(boolean showShortcut, char shortcutKey) {
   }

   public void setIcon(Drawable icon) {
      if(icon != null) {
         icon = DrawableCompat.wrap(icon.getConstantState().newDrawable()).mutate();
         icon.setBounds(0, 0, this.mIconSize, this.mIconSize);
         DrawableCompat.setTintList(icon, this.mIconTintList);
      }

      TextViewCompat.setCompoundDrawablesRelative(this, icon, (Drawable)null, (Drawable)null, (Drawable)null);
   }

   public boolean prefersCondensedTitle() {
      return false;
   }

   public boolean showsIcon() {
      return true;
   }

   protected int[] onCreateDrawableState(int extraSpace) {
      int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
      if(this.mItemData != null && this.mItemData.isCheckable() && this.mItemData.isChecked()) {
         mergeDrawableStates(drawableState, CHECKED_STATE_SET);
      }

      return drawableState;
   }

   void setIconTintList(ColorStateList tintList) {
      this.mIconTintList = tintList;
      if(this.mItemData != null) {
         this.setIcon(this.mItemData.getIcon());
      }

   }
}
