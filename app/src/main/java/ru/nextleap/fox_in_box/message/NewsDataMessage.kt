package ru.nextleap.fox_in_box.message

import ru.nextleap.fox_in_box.data.News
import ru.nextleap.sl.message.DataMessage

class NewsDataMessage(address: String, data: News): DataMessage(address, data)