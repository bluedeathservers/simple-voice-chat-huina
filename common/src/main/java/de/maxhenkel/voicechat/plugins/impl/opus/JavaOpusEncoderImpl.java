private void open() {
    if (opusEncoder != null) {
        return;
    }
    try {
        opusEncoder = new OpusEncoder(sampleRate, 1, getApplication(application));

        // --- НАСТРОЙКА ДЛЯ МУЗЫКИ (отключаем голосовой режим) ---
        // 1. Форсируем CELT (музыкальный режим, без SILK)
        opusEncoder.setForceMode(OpusMode.MODE_CELT_ONLY);
        // 2. Форсируем полную полосу 20 кГц (убирает "глухость" на тихих местах)
        opusEncoder.setBandwidth(OpusBandwidth.OPUS_BANDWIDTH_FULLBAND);
        // 3. Поднимаем битрейт (96-128 kbps для музыки)
        opusEncoder.setBitrate(128000);
        // 4. Отключаем DTX (прерывистую передачу, которая дает артефакты на музыке)
        opusEncoder.setUseDTX(false);
        // 5. Отключаем FEC (если ты еще этого не сделал в других местах)
        opusEncoder.setUseInbandFEC(false);
        opusEncoder.setPacketLossPercent(0);
        // 6. Указываем, что сигнал — музыка (если метод есть)
        // opusEncoder.setSignalType(OpusSignal.OPUS_SIGNAL_MUSIC);

    } catch (Exception e) {
        throw new IllegalStateException("Failed to create Opus encoder", e);
    }
}
