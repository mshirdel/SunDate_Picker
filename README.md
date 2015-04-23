SunDate Picker
=================

** کتابخونه ای برای انتخاب تاریخ شمسی در اندروید.

![](assets/screen-shot.jpg)

 این کتابخونه برای استفاده در برنامه [مکتب](http://cafebazaar.ir/app/com.afkar.maktab) نوشته شده.
 ----------
> درصورت استفاده از این کتابخونه لطفا در توضیحات نام کتابخونه رو ذکر کنید.



استفاده
---------

1. کتابخونه رو در برنامتون وارد کنید

2. OnDateSetListener رو در فایل جاوا خود Implement کنید

2. کد های زیر رو بنویسید

    ```java
    // ایجاد یک نمونه.
    DatePickerDialog dp = DatePickerDialog.newInstance(this, false);

    // انتخاب رنگ
    dp.setColor(color);
	
	// کد درخواست
	dp.setRequestID(requestID);
	
	// ویبره هنگام کلیک
	dp.setVibrate(vibrate);
	
	// کمترین و بیشترین سالی که باید نمایش داده شود
	dp.setYearRange(minYear, maxYear);
	
	// انتخاب فونت
	dp.setTypeFace(typeface);
	
	// تاریخی که در ابتدا باید نمایش داده شود
	dp.setInitialDate(year, month, day);
	
	// و در آخر نمایش
	dp.show(getSupportFragmentManager(), "");
    ```

نمونه
----------

در پروژه برنامه ی نمونه ی نوشته شده که کار با این کتابخونه رو نمایش میده

تغییرات
---------

**03/02/94**

* طراحی جدید
* درنظر گرفتن سال کبیسه


License
----------

    Copyright 2014 Alireza Afkar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
