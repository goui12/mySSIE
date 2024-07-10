/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysiexperience;

/**
 *
 * @author z004kptc
 */
public class PWSLData {
    private String txfCustValue;
    private String txfEmpValue;
    private String txfEquipValue;
    private String txfTaskValue;
    
    private boolean rdOnSiteYesValue;
    private boolean rdOnSiteNoValue;
    
    private boolean cbConfinedValue;
    private boolean cbCorrectionsValue;
    private boolean cbCutValue;
    private boolean cbDrivingValue;
    private boolean cbDustValue;
    private boolean cbErgonomicsValue;
    private boolean cbElectricalValue;
    private boolean cbEyeStrainValue;
    private boolean cbLiftsValue;
    private boolean cbLaddersValue;
    private boolean cbRoofsValue;
    private boolean cbFlyingValue;
    private boolean cbMetalValue;
    private boolean cbHandValue;
    private boolean cbHousekeepingValue;
    private boolean cbInfectionValue;
    private boolean cbLightingValue;
    private boolean cbHeavyValue;
    private boolean cbRepeatValue;
    private boolean cbNoiseValue;
    private boolean cbNewSiteValue;
    private boolean cbPowerToolsValue;
    private boolean cbSlipValue;
    private boolean cbTrenchValue;
    private boolean cbHotValue;
    private boolean cbColdValue;
    private boolean cbStormValue;
    private boolean cbInsectsValue;
    private boolean cbAloneValue;
    private boolean cbTripValue;
    
    private String txfToolsValue;
    private String txfNeedsValue;
    private String txfRepeatValue;
    private String txfUniqueValue;
    private String txaHazardValue;
    
    private boolean rbPPENoValue;
    private boolean rbPPEYesValue;
    private boolean rbSafetyNoValue;
    private boolean rbSafetyYesValue;
    
    private boolean cbExposedValue;
    private boolean cbSiemensLadderValue;
    private boolean cbHotWorkValue;
    private boolean cbInLiftValue;
    private boolean cbConfinedSpaceValue;
    private boolean cbNoneValue;
    private boolean cbFranticValue;
    private boolean cbEasyValue;
    private boolean cbFasterValue;
    private boolean cbNormalValue;
    private boolean cbSlowValue;

    // Constructors
    public PWSLData() {
    }

    public PWSLData(String txfRepeatValue, String txfCustValue, String txfEmpValue, String txfEquipValue, String txfTaskValue,
            boolean rdOnSiteYesValue, boolean rdOnSiteNoValue, boolean cbConfinedValue, boolean cbCorrectionsValue,
            boolean cbCutValue, boolean cbDrivingValue, boolean cbDustValue, boolean cbErgonomicsValue,
            boolean cbElectricalValue, boolean cbEyeStrainValue, boolean cbLiftsValue, boolean cbLaddersValue,
            boolean cbRoofsValue, boolean cbFlyingValue, boolean cbMetalValue, boolean cbHandValue,
            boolean cbHousekeepingValue, boolean cbInfectionValue, boolean cbLightingValue, boolean cbHeavyValue,
            boolean cbRepeatValue, boolean cbNoiseValue, boolean cbNewSiteValue, boolean cbPowerToolsValue,
            boolean cbSlipValue, boolean cbTrenchValue, boolean cbHotValue, boolean cbColdValue, boolean cbStormValue,
            boolean cbInsectsValue, boolean cbAloneValue, boolean cbTripValue, String txfToolsValue,
            String txfNeedsValue, String txfUniqueValue, String txaHazardValue, boolean rbPPENoValue, boolean rbPPEYesValue,
            boolean rbSafetyNoValue, boolean rbSafetyYesValue, boolean cbExposedValue, boolean cbSiemensLadderValue,
            boolean cbHotWorkValue, boolean cbInLiftValue, boolean cbConfinedSpaceValue, boolean cbNoneValue,
            boolean cbFranticValue, boolean cbEasyValue, boolean cbFasterValue, boolean cbNormalValue,
            boolean cbSlowValue) {
        this.txaHazardValue = txaHazardValue;
        this.txfCustValue = txfCustValue;
        this.txfEmpValue = txfEmpValue;
        this.txfEquipValue = txfEquipValue;
        this.txfTaskValue = txfTaskValue;
        this.txfRepeatValue = txfRepeatValue;
        this.rdOnSiteYesValue = rdOnSiteYesValue;
        this.rdOnSiteNoValue = rdOnSiteNoValue;
        this.cbConfinedValue = cbConfinedValue;
        this.cbCorrectionsValue = cbCorrectionsValue;
        this.cbCutValue = cbCutValue;
        this.cbDrivingValue = cbDrivingValue;
        this.cbDustValue = cbDustValue;
        this.cbErgonomicsValue = cbErgonomicsValue;
        this.cbElectricalValue = cbElectricalValue;
        this.cbEyeStrainValue = cbEyeStrainValue;
        this.cbLiftsValue = cbLiftsValue;
        this.cbLaddersValue = cbLaddersValue;
        this.cbRoofsValue = cbRoofsValue;
        this.cbFlyingValue = cbFlyingValue;
        this.cbMetalValue = cbMetalValue;
        this.cbHandValue = cbHandValue;
        this.cbHousekeepingValue = cbHousekeepingValue;
        this.cbInfectionValue = cbInfectionValue;
        this.cbLightingValue = cbLightingValue;
        this.cbHeavyValue = cbHeavyValue;
        this.cbRepeatValue = cbRepeatValue;
        this.cbNoiseValue = cbNoiseValue;
        this.cbNewSiteValue = cbNewSiteValue;
        this.cbPowerToolsValue = cbPowerToolsValue;
        this.cbSlipValue = cbSlipValue;
        this.cbTrenchValue = cbTrenchValue;
        this.cbHotValue = cbHotValue;
        this.cbColdValue = cbColdValue;
        this.cbStormValue = cbStormValue;
        this.cbInsectsValue = cbInsectsValue;
        this.cbAloneValue = cbAloneValue;
        this.cbTripValue = cbTripValue;
        this.txfToolsValue = txfToolsValue;
        this.txfNeedsValue = txfNeedsValue;
        this.txfUniqueValue = txfUniqueValue;
        this.rbPPENoValue = rbPPENoValue;
        this.rbPPEYesValue = rbPPEYesValue;
        this.rbSafetyNoValue = rbSafetyNoValue;
        this.rbSafetyYesValue = rbSafetyYesValue;
        this.cbExposedValue = cbExposedValue;
        this.cbSiemensLadderValue = cbSiemensLadderValue;
        this.cbHotWorkValue = cbHotWorkValue;
        this.cbInLiftValue = cbInLiftValue;
        this.cbConfinedSpaceValue = cbConfinedSpaceValue;
        this.cbNoneValue = cbNoneValue;
        this.cbFranticValue = cbFranticValue;
        this.cbEasyValue = cbEasyValue;
        this.cbFasterValue = cbFasterValue;
        this.cbNormalValue = cbNormalValue;
        this.cbSlowValue = cbSlowValue;
    }
    
    public String getTxaHazardValue() {
        System.out.println("TESTING Txa hazard = " + txaHazardValue);
        return txaHazardValue;
    }
    
    public void setTxaHazardValue(String txaHazardValue) {
        this.txaHazardValue = txaHazardValue;
    }
    
    public String getTxfRepeatValue(){
        return txfRepeatValue;
    }
    
    public void setTxfRepeatValue (String txfRepeatValue) {
        this.txfRepeatValue = txfRepeatValue;
    }
    public String getTxfCustValue() {
        return txfCustValue;
    }

    public void setTxfCustValue(String txfCustValue) {
        this.txfCustValue = txfCustValue;
    }

    public String getTxfEmpValue() {
        return txfEmpValue;
    }

    public void setTxfEmpValue(String txfEmpValue) {
        this.txfEmpValue = txfEmpValue;
    }

    public String getTxfEquipValue() {
        return txfEquipValue;
    }

    public void setTxfEquipValue(String txfEquipValue) {
        this.txfEquipValue = txfEquipValue;
    }

    public String getTxfTaskValue() {
        return txfTaskValue;
    }

    public void setTxfTaskValue(String txfTaskValue) {
        this.txfTaskValue = txfTaskValue;
    }

    public boolean isRdOnSiteYesValue() {
        return rdOnSiteYesValue;
    }

    public void setRdOnSiteYesValue(boolean rdOnSiteYesValue) {
        this.rdOnSiteYesValue = rdOnSiteYesValue;
    }

    public boolean isRdOnSiteNoValue() {
        return rdOnSiteNoValue;
    }

    public void setRdOnSiteNoValue(boolean rdOnSiteNoValue) {
        this.rdOnSiteNoValue = rdOnSiteNoValue;
    }

    public boolean isCbConfinedValue() {
        return cbConfinedValue;
    }

    public void setCbConfinedValue(boolean cbConfinedValue) {
        this.cbConfinedValue = cbConfinedValue;
    }

    public boolean isCbCorrectionsValue() {
        return cbCorrectionsValue;
    }

    public void setCbCorrectionsValue(boolean cbCorrectionsValue) {
        this.cbCorrectionsValue = cbCorrectionsValue;
    }

    public boolean isCbCutValue() {
        return cbCutValue;
    }

    public void setCbCutValue(boolean cbCutValue) {
        this.cbCutValue = cbCutValue;
    }

    public boolean isCbDrivingValue() {
        return cbDrivingValue;
    }

    public void setCbDrivingValue(boolean cbDrivingValue) {
        this.cbDrivingValue = cbDrivingValue;
    }

    public boolean isCbDustValue() {
        return cbDustValue;
    }

    public void setCbDustValue(boolean cbDustValue) {
        this.cbDustValue = cbDustValue;
    }

    public boolean isCbErgonomicsValue() {
        return cbErgonomicsValue;
    }

    public void setCbErgonomicsValue(boolean cbErgonomicsValue) {
        this.cbErgonomicsValue = cbErgonomicsValue;
    }

    public boolean isCbElectricalValue() {
        return cbElectricalValue;
    }

    public void setCbElectricalValue(boolean cbElectricalValue) {
        this.cbElectricalValue = cbElectricalValue;
    }

    public boolean isCbEyeStrainValue() {
        return cbEyeStrainValue;
    }

    public void setCbEyeStrainValue(boolean cbEyeStrainValue) {
        this.cbEyeStrainValue = cbEyeStrainValue;
    }

    public boolean isCbLiftsValue() {
        return cbLiftsValue;
    }

    public void setCbLiftsValue(boolean cbLiftsValue) {
        this.cbLiftsValue = cbLiftsValue;
    }

    public boolean isCbLaddersValue() {
        return cbLaddersValue;
    }

    public void setCbLaddersValue(boolean cbLaddersValue) {
        this.cbLaddersValue = cbLaddersValue;
    }

    public boolean isCbRoofsValue() {
        return cbRoofsValue;
    }

    public void setCbRoofsValue(boolean cbRoofsValue) {
        this.cbRoofsValue = cbRoofsValue;
    }

    public boolean isCbFlyingValue() {
        return cbFlyingValue;
    }

    public void setCbFlyingValue(boolean cbFlyingValue) {
        this.cbFlyingValue = cbFlyingValue;
    }

    public boolean isCbMetalValue() {
        return cbMetalValue;
    }

    public void setCbMetalValue(boolean cbMetalValue) {
        this.cbMetalValue = cbMetalValue;
    }

    public boolean isCbHandValue() {
        return cbHandValue;
    }

    public void setCbHandValue(boolean cbHandValue) {
        this.cbHandValue = cbHandValue;
    }

    public boolean isCbHousekeepingValue() {
        return cbHousekeepingValue;
    }

    public void setCbHousekeepingValue(boolean cbHousekeepingValue) {
        this.cbHousekeepingValue = cbHousekeepingValue;
    }

    public boolean isCbInfectionValue() {
        return cbInfectionValue;
    }

    public void setCbInfectionValue(boolean cbInfectionValue) {
        this.cbInfectionValue = cbInfectionValue;
    }

    public boolean isCbLightingValue() {
        return cbLightingValue;
    }

    public void setCbLightingValue(boolean cbLightingValue) {
        this.cbLightingValue = cbLightingValue;
    }

    public boolean isCbHeavyValue() {
        return cbHeavyValue;
    }

    public void setCbHeavyValue(boolean cbHeavyValue) {
        this.cbHeavyValue = cbHeavyValue;
    }

    public boolean isCbRepeatValue() {
        return cbRepeatValue;
    }

    public void setCbRepeatValue(boolean cbRepeatValue) {
        this.cbRepeatValue = cbRepeatValue;
    }

    public boolean isCbNoiseValue() {
        return cbNoiseValue;
    }

    public void setCbNoiseValue(boolean cbNoiseValue) {
        this.cbNoiseValue = cbNoiseValue;
    }

    public boolean isCbNewSiteValue() {
        return cbNewSiteValue;
    }

    public void setCbNewSiteValue(boolean cbNewSiteValue) {
        this.cbNewSiteValue = cbNewSiteValue;
    }

    public boolean isCbPowerToolsValue() {
        return cbPowerToolsValue;
    }

    public void setCbPowerToolsValue(boolean cbPowerToolsValue) {
        this.cbPowerToolsValue = cbPowerToolsValue;
    }

    public boolean isCbSlipValue() {
        return cbSlipValue;
    }

    public void setCbSlipValue(boolean cbSlipValue) {
        this.cbSlipValue = cbSlipValue;
    }

    public boolean isCbTrenchValue() {
        return cbTrenchValue;
    }

    public void setCbTrenchValue(boolean cbTrenchValue) {
        this.cbTrenchValue = cbTrenchValue;
    }

    public boolean isCbHotValue() {
        return cbHotValue;
    }

    public void setCbHotValue(boolean cbHotValue) {
        this.cbHotValue = cbHotValue;
    }

    public boolean isCbColdValue() {
        return cbColdValue;
    }

    public void setCbColdValue(boolean cbColdValue) {
        this.cbColdValue = cbColdValue;
    }

    public boolean isCbStormValue() {
        return cbStormValue;
    }

    public void setCbStormValue(boolean cbStormValue) {
        this.cbStormValue = cbStormValue;
    }

    public boolean isCbInsectsValue() {
        return cbInsectsValue;
    }

    public void setCbInsectsValue(boolean cbInsectsValue) {
        this.cbInsectsValue = cbInsectsValue;
    }

    public boolean isCbAloneValue() {
        return cbAloneValue;
    }

    public void setCbAloneValue(boolean cbAloneValue) {
        this.cbAloneValue = cbAloneValue;
    }

    public boolean isCbTripValue() {
        return cbTripValue;
    }

    public void setCbTripValue(boolean cbTripValue) {
        this.cbTripValue = cbTripValue;
    }

    public String getTxfToolsValue() {
        return txfToolsValue;
    }

    public void setTxfToolsValue(String txfToolsValue) {
        this.txfToolsValue = txfToolsValue;
    }

    public String getTxfNeedsValue() {
        return txfNeedsValue;
    }

    public void setTxfNeedsValue(String txfNeedsValue) {
        this.txfNeedsValue = txfNeedsValue;
    }

    public String getTxfUniqueValue() {
        return txfUniqueValue;
    }

    public void setTxfUniqueValue(String txfUniqueValue) {
        this.txfUniqueValue = txfUniqueValue;
    }

    public boolean isRbPPENoValue() {
        return rbPPENoValue;
    }

    public void setRbPPENoValue(boolean rbPPENoValue) {
        this.rbPPENoValue = rbPPENoValue;
    }

    public boolean isRbPPEYesValue() {
        return rbPPEYesValue;
    }

    public void setRbPPEYesValue(boolean rbPPEYesValue) {
        this.rbPPEYesValue = rbPPEYesValue;
    }

    public boolean isRbSafetyNoValue() {
        return rbSafetyNoValue;
    }

    public void setRbSafetyNoValue(boolean rbSafetyNoValue) {
        this.rbSafetyNoValue = rbSafetyNoValue;
    }

    public boolean isRbSafetyYesValue() {
        return rbSafetyYesValue;
    }

    public void setRbSafetyYesValue(boolean rbSafetyYesValue) {
        this.rbSafetyYesValue = rbSafetyYesValue;
    }

    public boolean isCbExposedValue() {
        return cbExposedValue;
    }

    public void setCbExposedValue(boolean cbExposedValue) {
        this.cbExposedValue = cbExposedValue;
    }

    public boolean isCbSiemensLadderValue() {
        return cbSiemensLadderValue;
    }

    public void setCbSiemensLadderValue(boolean cbSiemensLadderValue) {
        this.cbSiemensLadderValue = cbSiemensLadderValue;
    }

    public boolean isCbHotWorkValue() {
        return cbHotWorkValue;
    }

    public void setCbHotWorkValue(boolean cbHotWorkValue) {
        this.cbHotWorkValue = cbHotWorkValue;
    }

    public boolean isCbInLiftValue() {
        return cbInLiftValue;
    }

    public void setCbInLiftValue(boolean cbInLiftValue) {
        this.cbInLiftValue = cbInLiftValue;
    }

    public boolean isCbConfinedSpaceValue() {
        return cbConfinedSpaceValue;
    }

    public void setCbConfinedSpaceValue(boolean cbConfinedSpaceValue) {
        this.cbConfinedSpaceValue = cbConfinedSpaceValue;
    }

    public boolean isCbNoneValue() {
        return cbNoneValue;
    }

    public void setCbNoneValue(boolean cbNoneValue) {
        this.cbNoneValue = cbNoneValue;
    }

    public boolean isCbFranticValue() {
        return cbFranticValue;
    }

    public void setCbFranticValue(boolean cbFranticValue) {
        this.cbFranticValue = cbFranticValue;
    }

    public boolean isCbEasyValue() {
        return cbEasyValue;
    }

    public void setCbEasyValue(boolean cbEasyValue) {
        this.cbEasyValue = cbEasyValue;
    }

    public boolean isCbFasterValue() {
        return cbFasterValue;
    }

    public void setCbFasterValue(boolean cbFasterValue) {
        this.cbFasterValue = cbFasterValue;
    }

    public boolean isCbNormalValue() {
        return cbNormalValue;
    }

    public void setCbNormalValue(boolean cbNormalValue) {
        this.cbNormalValue = cbNormalValue;
    }

    public boolean isCbSlowValue() {
        return cbSlowValue;
    }

    public void setCbSlowValue(boolean cbSlowValue) {
        this.cbSlowValue = cbSlowValue;
    }
}

