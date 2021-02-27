package ru.nextleap.sl.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.nextleap.sl.INamed


@Suppress("UNCHECKED_CAST")
class BackStack {
    companion object {
        /**
         * Показать фрагмент
         */
        @JvmStatic
        fun showFragment(
            activity: AppCompatActivity, idRes: Int, fragment: Fragment, addToBackStack: Boolean,
            clearBackStack: Boolean,
            animate: Boolean, allowingStateLoss: Boolean, animation: Int? = null
        ) {
            val tag: String?
            val fm = activity.supportFragmentManager
            if (fragment is INamed) {
                tag = (fragment as INamed).getName()
            } else {
                tag = fragment::class.simpleName
            }
            if (clearBackStack) {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            val ft = fm.beginTransaction()
            if (addToBackStack) {
                ft.addToBackStack(tag)
            }
            if (animate) {
                if (animation != null) {
                    ft.setCustomAnimations(animation,animation)
                } else {
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                }
            }
            ft.replace(idRes, fragment, tag)
            if (allowingStateLoss) {
                ft.commitAllowingStateLoss()
            } else {
                ft.commit()
            }
        }

        /**
         * Переключиться на фрагмент верхнего уровня
         */
        @JvmStatic
        fun switchToTopFragment(activity: AppCompatActivity) {
            val fm = activity.supportFragmentManager
            val backStackEntryCount = fm.backStackEntryCount
            if (backStackEntryCount > 0) {
                for (i in backStackEntryCount - 1 downTo 0) {
                    val backStackEntry = fm.getBackStackEntryAt(i)
                    val fragment = fm.findFragmentByTag(backStackEntry.name)
                    if (fragment is OnBackPressListener) {
                        if ((fragment as OnBackPressListener).isTop()) {
                            fm.popBackStackImmediate(backStackEntry.name, 0)
                        }
                    }
                }
            }
        }

        /**
         * Переключиться на фрагмент
         */
        @JvmStatic
        fun switchToFragment(activity: AppCompatActivity, name: String): Boolean {
            val fm = activity.supportFragmentManager
            val list = fm.fragments
            for (fragment in list) {
                if (fragment is INamed) {
                    val abstractFragment = fragment as INamed
                    if (name == abstractFragment.getName()) {
                        if (fm.popBackStackImmediate(abstractFragment.getName(), 0)) {
                            return true
                        }
                    }
                }
            }
            return false
        }

        /**
         * Отработать нажатие на BackPressed
         */
        @JvmStatic
        fun onBackPressed(activity: AbsActivity) {
            val fm = activity.supportFragmentManager
            val backStackEntryCount = fm.backStackEntryCount
            if (backStackEntryCount > 0) {
                val backStackEntry = fm
                    .getBackStackEntryAt(backStackEntryCount - 1)
                val fragment = fm.findFragmentByTag(backStackEntry.name)
                val onBackPressListener: OnBackPressListener?
                if (fragment is OnBackPressListener) {
                    onBackPressListener = fragment
                } else {
                    onBackPressListener = null
                }

                if (onBackPressListener == null) {
                    activity.onActivityBackPressed()
                } else {
                    if (!onBackPressListener.onBackPressed()) {
                        activity.onActivityBackPressed()
                    }
                }
            } else {
                activity.stop()
            }
        }

        /**
         * Проверить наличие фрагмента верхнего уровня
         */
        @JvmStatic
        fun hasTopFragment(activity: AppCompatActivity): Boolean {
            val fm = activity.supportFragmentManager
            val backStackEntryCount = fm.backStackEntryCount
            if (backStackEntryCount > 0) {
                for (i in backStackEntryCount - 1 downTo 0) {
                    val backStackEntry = fm
                        .getBackStackEntryAt(i)
                    val fragment = fm.findFragmentByTag(backStackEntry.name)
                    if (fragment is OnBackPressListener) {
                        if ((fragment as OnBackPressListener).isTop()) {
                            return true
                        }
                    }
                }
            }
            return false
        }

        /**
         * Проверить наличие фрагмента
         */
        @JvmStatic
        fun hasFragment(activity: AppCompatActivity, name: String): Boolean {
            val fm = activity.supportFragmentManager
            val backStackEntryCount = fm.backStackEntryCount
            if (backStackEntryCount > 0) {
                for (i in backStackEntryCount - 1 downTo 0) {
                    val backStackEntry = fm
                        .getBackStackEntryAt(i)
                    val fragment = fm.findFragmentByTag(backStackEntry.name)
                    val tag: String?
                    if (fragment is INamed) {
                        tag = fragment.getName()
                    } else {
                        tag = fragment!!::class.simpleName
                    }
                    if (name == tag) {
                        return true
                    }
                }
            }
            return false
        }

        /**
         * Проверить является ли фрагмент текущим
         */
        @JvmStatic
        fun isCurrentFragment(activity: AppCompatActivity, name: String): Boolean {
            val fm = activity.supportFragmentManager
            val backStackEntryCount = fm.backStackEntryCount
            if (backStackEntryCount > 0) {
                val backStackEntry = fm
                    .getBackStackEntryAt(backStackEntryCount - 1)
                val fragment = fm.findFragmentByTag(backStackEntry.name)
                val tag: String?
                if (fragment is INamed) {
                    tag = fragment.getName()
                } else {
                    tag = fragment!!::class.simpleName
                }
                if (name == tag) {
                    return true
                }
            }
            return false
        }

        /**
         * Проверить BackStack пуст
         */
        @JvmStatic
        fun isBackStackEmpty(activity: AppCompatActivity): Boolean {
            return activity.supportFragmentManager.backStackEntryCount == 0
        }

        /**
         * Получить количество записей в BackStack
         */
        @JvmStatic
        fun getBackStackEntryCount(activity: AppCompatActivity): Int {
            return activity.supportFragmentManager.backStackEntryCount
        }

        /**
         * Получить фрагмент
         */
        @JvmStatic
        fun <F> getFragment(activity: AppCompatActivity, cls: Class<F>, id: Int): F? {
            var f: F? = null
            try {
                f = cls.cast(activity.supportFragmentManager.findFragmentById(id))
            } catch (e: ClassCastException) {
            }

            return f
        }

        /**
         * Получить текущий фрагмент
         */
        @JvmStatic
        fun <F> getCurrentFragment(activity: AppCompatActivity): F? {
            val fm = activity.supportFragmentManager
            val backStackEntryCount = fm.backStackEntryCount
            if (backStackEntryCount > 0) {
                val backStackEntry = fm
                    .getBackStackEntryAt(backStackEntryCount - 1)
                return fm.findFragmentByTag(backStackEntry.name) as F?
            }
            return null
        }

        /**
         * Очистить BackStack
         */
        @JvmStatic
        fun clearBackStack(activity: AppCompatActivity) {
            val fm = activity.supportFragmentManager
            for (i in 0 until fm.backStackEntryCount) {
                fm.popBackStack()
            }
        }


    }


}
