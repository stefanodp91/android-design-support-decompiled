package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
 import nz.xbc.designsupportdecompilation.R.attr; import nz.xbc.designsupportdecompilation.R.style;  import nz.xbc.designsupportdecompilation.R.styleable;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.internal.view.SupportMenuInflater;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;

public class NavigationView extends ScrimInsetsFrameLayout {
   private static final int[] CHECKED_STATE_SET = new int[]{16842912};
   private static final int[] DISABLED_STATE_SET = new int[]{-16842910};
   private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;
   private final MenuBuilder mMenu;
   private final NavigationMenuPresenter mPresenter;
   private NavigationView.OnNavigationItemSelectedListener mListener;
   private int mMaxWidth;
   private MenuInflater mMenuInflater;

   public NavigationView(Context context) {
      this(context, (AttributeSet)null);
   }

   public NavigationView(Context context, AttributeSet attrs) {
      this(context, attrs, 0);
   }

   public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      this.mMenu = new MenuBuilder(context);
      TypedArray a = context.obtainStyledAttributes(attrs, styleable.NavigationView, defStyleAttr, style.Widget_Design_NavigationView);
      this.setBackgroundDrawable(a.getDrawable(styleable.NavigationView_android_background));
      if(a.hasValue(styleable.NavigationView_elevation)) {
         ViewCompat.setElevation(this, (float)a.getDimensionPixelSize(styleable.NavigationView_elevation, 0));
      }

      ViewCompat.setFitsSystemWindows(this, a.getBoolean(styleable.NavigationView_android_fitsSystemWindows, false));
      this.mMaxWidth = a.getDimensionPixelSize(styleable.NavigationView_android_maxWidth, 0);
      ColorStateList itemIconTint;
      if(a.hasValue(styleable.NavigationView_itemIconTint)) {
         itemIconTint = a.getColorStateList(styleable.NavigationView_itemIconTint);
      } else {
         itemIconTint = this.createDefaultColorStateList(16842808);
      }

      ColorStateList itemTextColor;
      if(a.hasValue(styleable.NavigationView_itemTextColor)) {
         itemTextColor = a.getColorStateList(styleable.NavigationView_itemTextColor);
      } else {
         itemTextColor = this.createDefaultColorStateList(16842806);
      }

      Drawable itemBackground = a.getDrawable(styleable.NavigationView_itemBackground);
      if(a.hasValue(styleable.NavigationView_menu)) {
         this.inflateMenu(a.getResourceId(styleable.NavigationView_menu, 0));
      }

      this.mMenu.setCallback(new Callback() {
         public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return NavigationView.this.mListener != null && NavigationView.this.mListener.onNavigationItemSelected(item);
         }

         public void onMenuModeChange(MenuBuilder menu) {
         }
      });
      this.mPresenter = new NavigationMenuPresenter();
      this.mPresenter.setId(1);
      this.mPresenter.initForMenu(context, this.mMenu);
      this.mPresenter.setItemIconTintList(itemIconTint);
      this.mPresenter.setItemTextColor(itemTextColor);
      this.mPresenter.setItemBackground(itemBackground);
      this.mMenu.addMenuPresenter(this.mPresenter);
      this.addView((View)this.mPresenter.getMenuView(this));
      if(a.hasValue(styleable.NavigationView_headerLayout)) {
         this.inflateHeaderView(a.getResourceId(styleable.NavigationView_headerLayout, 0));
      }

      a.recycle();
   }

   protected Parcelable onSaveInstanceState() {
      Parcelable superState = super.onSaveInstanceState();
      NavigationView.SavedState state = new NavigationView.SavedState(superState);
      state.menuState = new Bundle();
      this.mMenu.savePresenterStates(state.menuState);
      return state;
   }

   protected void onRestoreInstanceState(Parcelable savedState) {
      NavigationView.SavedState state = (NavigationView.SavedState)savedState;
      super.onRestoreInstanceState(state.getSuperState());
      this.mMenu.restorePresenterStates(state.menuState);
   }

   public void setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener listener) {
      this.mListener = listener;
   }

   protected void onMeasure(int widthSpec, int heightSpec) {
      switch(MeasureSpec.getMode(widthSpec)) {
      case Integer.MIN_VALUE:
         widthSpec = MeasureSpec.makeMeasureSpec(Math.min(MeasureSpec.getSize(widthSpec), this.mMaxWidth), 1073741824);
         break;
      case 0:
         widthSpec = MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824);
      case 1073741824:
      }

      super.onMeasure(widthSpec, heightSpec);
   }

   public void inflateMenu(int resId) {
      if(this.mPresenter != null) {
         this.mPresenter.setUpdateSuspended(true);
      }

      this.getMenuInflater().inflate(resId, this.mMenu);
      if(this.mPresenter != null) {
         this.mPresenter.setUpdateSuspended(false);
         this.mPresenter.updateMenuView(false);
      }

   }

   public Menu getMenu() {
      return this.mMenu;
   }

   public View inflateHeaderView(@LayoutRes int res) {
      return this.mPresenter.inflateHeaderView(res);
   }

   public void addHeaderView(@NonNull View view) {
      this.mPresenter.addHeaderView(view);
   }

   public void removeHeaderView(@NonNull View view) {
      this.mPresenter.removeHeaderView(view);
   }

   @Nullable
   public ColorStateList getItemIconTintList() {
      return this.mPresenter.getItemTintList();
   }

   public void setItemIconTintList(@Nullable ColorStateList tint) {
      this.mPresenter.setItemIconTintList(tint);
   }

   @Nullable
   public ColorStateList getItemTextColor() {
      return this.mPresenter.getItemTextColor();
   }

   public void setItemTextColor(@Nullable ColorStateList textColor) {
      this.mPresenter.setItemTextColor(textColor);
   }

   public Drawable getItemBackground() {
      return this.mPresenter.getItemBackground();
   }

   public void setItemBackgroundResource(@DrawableRes int resId) {
      this.setItemBackground(ContextCompat.getDrawable(this.getContext(), resId));
   }

   public void setItemBackground(Drawable itemBackground) {
      this.mPresenter.setItemBackground(itemBackground);
   }

   private MenuInflater getMenuInflater() {
      if(this.mMenuInflater == null) {
         this.mMenuInflater = new SupportMenuInflater(this.getContext());
      }

      return this.mMenuInflater;
   }

   private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
      TypedValue value = new TypedValue();
      if(!this.getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
         return null;
      } else {
         ColorStateList baseColor = this.getResources().getColorStateList(value.resourceId);
         if(!this.getContext().getTheme().resolveAttribute(attr.colorPrimary, value, true)) {
            return null;
         } else {
            int colorPrimary = value.data;
            int defaultColor = baseColor.getDefaultColor();
            return new ColorStateList(new int[][]{DISABLED_STATE_SET, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{baseColor.getColorForState(DISABLED_STATE_SET, defaultColor), colorPrimary, defaultColor});
         }
      }
   }

   public static class SavedState extends BaseSavedState {
      public Bundle menuState;
      public static final Creator CREATOR = new Creator() {
         public NavigationView.SavedState createFromParcel(Parcel parcel) {
            return new NavigationView.SavedState(parcel);
         }

         public NavigationView.SavedState[] newArray(int size) {
            return new NavigationView.SavedState[size];
         }
      };

      public SavedState(Parcel in) {
         super(in);
         this.menuState = in.readBundle();
      }

      public SavedState(Parcelable superState) {
         super(superState);
      }

      public void writeToParcel(@NonNull Parcel dest, int flags) {
         super.writeToParcel(dest, flags);
         dest.writeBundle(this.menuState);
      }
   }

   public interface OnNavigationItemSelectedListener {
      boolean onNavigationItemSelected(MenuItem var1);
   }
}
